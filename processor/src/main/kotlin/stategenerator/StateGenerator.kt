package com.github.rougsig.rxflux.processor.stategenerator

import com.github.rougsig.rxflux.processor.base.*
import com.github.rougsig.rxflux.processor.extensions.beginWithUpperCase
import com.squareup.kotlinpoet.*

internal val stateGenerator = StateGenerator()

private const val MAP_FIELD_NAME = "map"
private const val FIELDS_INTERFACE_NAME = "Fields"
private const val ALL_FIELDS_FIELD_NAME = "ALL_FIELDS"
private const val FIELDS_FIELD_NAME = "FIELDS"
private const val FIELD_PARAM_NAME = "field"
private const val VALUE_PARAM_NAME = "value"
private const val SET_FIELD_FUN_NAME = "setField"
private const val GET_FIELD_FUN_NAME = "getField"

internal class StateGenerator : Generator<StateType> {
  override fun generateFile(type: StateType): FileSpec {
    val stateType = TypeVariableName(type.stateName)

    return FileSpec
      .builder(type.packageName, type.stateName)
      .addImport(KOTLINX_IMMUTABLE, IMMUTABLE_HASH_MAP_OF)
      .addType(TypeSpec
        .classBuilder(type.stateName)
        .addFluxStateImplementation(stateType)
        .addFieldsInterface(stateType, type.fields)
        .addConstructor(type.fields)
        .addFields(type.stateName, type.fields)
        .addCompanionObject(stateType, type.fields)
        .addToString()
        .build())
      .build()
  }

  private fun TypeSpec.Builder.addConstructor(fields: List<FieldType>) = apply {
    val mapType = createParameterizedImmutableMap(ANY_TYPE_NAME, ANY_NULLABLE_TYPE_NAME)

    this
      .primaryConstructor(FunSpec
        .constructorBuilder()
        .addParameter(MAP_FIELD_NAME, mapType, KModifier.PRIVATE)
        .build())
      .addProperty(PropertySpec
        .builder(MAP_FIELD_NAME, mapType)
        .initializer(MAP_FIELD_NAME)
        .build())
      .addFunction(FunSpec
        .constructorBuilder()
        .addParameters(fields.map { ParameterSpec.builder(it.name, it.type).build() })
        .callThisConstructor(CodeBlock.of(
          "%L = %L(\n${fields.joinToString(",\n") { "  %S to %L" }}\n)",
          MAP_FIELD_NAME, IMMUTABLE_HASH_MAP_OF, *fields.flatMap { listOf(it.name, it.name) }.toTypedArray()))
        .build())
  }

  private fun TypeSpec.Builder.addToString() = apply {
    this
      .addFunction(FunSpec
        .builder("toString")
        .addModifiers(KModifier.OVERRIDE)
        .addStatement("return %L.toString()", MAP_FIELD_NAME)
        .build())
  }

  private fun TypeSpec.Builder.addFields(stateName: String, fields: List<FieldType>) = apply {
    fields.forEach { field ->
      this
        .addProperty(PropertySpec
          .builder(field.name, field.type)
          .getter(FunSpec
            .getterBuilder()
            .addStatement("return %L[%S] as %T", MAP_FIELD_NAME, field.name, field.type)
            .build())
          .build())
        .addFunction(FunSpec
          .builder("set${field.name.beginWithUpperCase()}")
          .addParameter(field.name, field.type)
          .addStatement("return %L(%L.put(%S, %L))", stateName, MAP_FIELD_NAME, field.name, field.name)
          .build())
    }
  }

  private fun TypeSpec.Builder.addFieldsInterface(stateType: TypeName, fields: List<FieldType>) = apply {
    this
      .addType(TypeSpec
        .interfaceBuilder(FIELDS_INTERFACE_NAME)
        .addProperties(fields.map { field ->
          PropertySpec
            .builder(field.name, createParameterizedFluxStateField(field.type, stateType))
            .build()
        })
        .build())
  }

  private fun TypeSpec.Builder.addFluxStateImplementation(stateType: TypeName) = apply {
    this
      .addSuperinterface(createParameterizedFluxState(stateType))
      .addGetFieldFunction(stateType)
      .addSetFieldFunction(stateType)

  }

  private fun TypeSpec.Builder.addGetFieldFunction(stateType: TypeName) = apply {
    this
      .addFunction(FunSpec
        .builder(SET_FIELD_FUN_NAME)
        .addModifiers(KModifier.OVERRIDE)
        .addTypeVariable(TypeVariableName("T"))
        .addParameter(ParameterSpec
          .builder(FIELD_PARAM_NAME, createParameterizedFluxStateField(TypeVariableName("T"), stateType))
          .build())
        .addParameter(ParameterSpec
          .builder(VALUE_PARAM_NAME, TypeVariableName("T"))
          .build())
        .returns(stateType)
        .addCode("require(%L.contains(%L)) { \"\$%L not found in \$this\" }\n", ALL_FIELDS_FIELD_NAME, FIELD_PARAM_NAME, FIELD_PARAM_NAME)
        .addCode("return %L(%L.put(%L.name, %L))\n", stateType, MAP_FIELD_NAME, FIELD_PARAM_NAME, VALUE_PARAM_NAME)
        .build())
  }

  private fun TypeSpec.Builder.addSetFieldFunction(stateType: TypeName) = apply {
    this
      .addFunction(FunSpec
        .builder(GET_FIELD_FUN_NAME)
        .addModifiers(KModifier.OVERRIDE)
        .addTypeVariable(TypeVariableName("T"))
        .addParameter(ParameterSpec
          .builder(FIELD_PARAM_NAME, createParameterizedFluxStateField(TypeVariableName("T"), stateType))
          .build())
        .returns(TypeVariableName("T"))
        .addCode("require(%L.contains(%L)) { \"\$%L not found in \$this\" }\n", ALL_FIELDS_FIELD_NAME, FIELD_PARAM_NAME, FIELD_PARAM_NAME)
        .addCode("return %L[%L.name] as T\n", MAP_FIELD_NAME, FIELD_PARAM_NAME)
        .build())
  }

  private fun TypeSpec.Builder.addCompanionObject(stateType: TypeName, fields: List<FieldType>) = apply {
    this
      .addType(TypeSpec
        .companionObjectBuilder()
        .addSuperinterface(createParameterizedFluxStateState(TypeVariableName(FIELDS_INTERFACE_NAME), stateType))
        .addProperty(PropertySpec
          .builder(FIELDS_FIELD_NAME, TypeVariableName(FIELDS_INTERFACE_NAME))
          .addModifiers(KModifier.OVERRIDE)
          .initializer(CodeBlock
            .builder()
            .addStatement("object : %L {", FIELDS_INTERFACE_NAME)
            .apply {
              fields.forEach { field ->
                addStatement(
                  "override val %L = %T(%S, \"%T\", %S)",
                  field.name,
                  createParameterizedFluxStateField(field.type, stateType),
                  field.name,
                  field.type,
                  stateType
                )
              }
            }
            .addStatement("}")
            .build())
          .build())
        .addProperty(PropertySpec
          .builder(ALL_FIELDS_FIELD_NAME, createParameterizedSet(createParameterizedFluxStateField(TypeVariableName("*"), stateType)))
          .addModifiers(KModifier.OVERRIDE)
          .initializer("setOf(${fields.joinToString { "$FIELDS_FIELD_NAME.${it.name}" }})")
          .build())
        .build())
  }
}
