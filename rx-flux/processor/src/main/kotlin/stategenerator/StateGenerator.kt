package com.github.rougsig.rxflux.processor.stategenerator

import com.github.rougsig.rxflux.processor.base.*
import com.github.rougsig.rxflux.processor.extensions.beginWithLowerCase
import com.github.rougsig.rxflux.processor.extensions.beginWithUpperCase
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

internal val stateGenerator = StateGenerator()

private const val MAP_FIELD_NAME = "map"
private const val OTHER_PARAM_NAME = "other"

internal class StateGenerator : Generator<StateType> {
  override fun generateFile(type: StateType): FileSpec {
    return FileSpec
      .builder(type.packageName, type.stateType.name)
      .addImport(KOTLINX_IMMUTABLE, IMMUTABLE_HASH_MAP_OF)
      .addImport(RXFLUX_CORE, RXFLUX_CORE_REDUCER)
      .addType(TypeSpec
        .classBuilder(type.stateType.name)
        .addConstructor(type.fields)
        .addCompanionObject(type.stateType, type.fields)
        .addFields(type.stateType, type.fields)
        .addToString(type.stateType, type.fields)
        .build())
      .build()
  }

  private fun TypeSpec.Builder.addConstructor(fields: List<FieldType>) = apply {
    val mapType = createParameterizedImmutableMap(ANY_TYPE_NAME, ANY_NULLABLE_TYPE_NAME)

    this
      .primaryConstructor(FunSpec
        .constructorBuilder()
        .addModifiers(KModifier.PRIVATE)
        .addParameter(MAP_FIELD_NAME, mapType, KModifier.PRIVATE)
        .build())
      .addProperty(PropertySpec
        .builder(MAP_FIELD_NAME, mapType)
        .initializer(MAP_FIELD_NAME)
        .build())
      .addFunction(FunSpec
        .constructorBuilder()
        .addParameters(fields.map { ParameterSpec.builder(it.name, it.type).build() })
        .callThisConstructor(CodeBlock
          .builder()
          .add("%L = %L(",
            MAP_FIELD_NAME, IMMUTABLE_HASH_MAP_OF)
          .apply {
            fields.forEachIndexed { index, field ->
              add("%S to %L%L",
                field.name, field.name, if (fields.lastIndex != index) "," else ")")
            }
          }
          .build())
        .build())
  }

  private fun TypeSpec.Builder.addToString(stateType: TypeName, fields: List<FieldType>) = apply {
    this
      .addFunction(FunSpec
        .builder("toString")
        .addModifiers(KModifier.OVERRIDE)
        .addStatement("return %L.toString()",
          MAP_FIELD_NAME)
        .build())
      .addFunction(FunSpec
        .builder("hashCode")
        .addModifiers(KModifier.OVERRIDE)
        .addStatement("return %L.hashCode()",
          MAP_FIELD_NAME)
        .build())
      .addFunction(FunSpec
        .builder("equals")
        .addModifiers(KModifier.OVERRIDE)
        .returns(Boolean::class.asTypeName())
        .addParameter(OTHER_PARAM_NAME, ANY_NULLABLE_TYPE_NAME)
        .addCode(CodeBlock
          .builder()
          .addStatement("if(%L !is %L) return false",
            OTHER_PARAM_NAME, stateType)
          .add("return ")
          .apply {
            fields.forEachIndexed { index, field ->
              add("%L[%S] == other.${field.name} %L ",
                MAP_FIELD_NAME, field.name, if (fields.lastIndex != index) "&&" else "")
            }
          }
          .build())
        .build())
  }

  private fun TypeSpec.Builder.addFields(stateType: TypeVariableName, fields: List<FieldType>) = apply {
    fields.forEach { field ->
      this
        .addProperty(PropertySpec
          .builder(field.name, field.type)
          .getter(FunSpec
            .getterBuilder()
            .addStatement("return %L[%S] as %T",
              MAP_FIELD_NAME, field.name, field.type)
            .build())
          .build())
        .addFunction(FunSpec
          .builder("set${field.name.beginWithUpperCase()}")
          .addParameter(field.name, field.type)
          .addStatement("return %L(%L.put(%S, %L))",
            stateType, MAP_FIELD_NAME, field.name, field.name)
          .build())
    }
  }

  private fun TypeSpec.Builder.addCompanionObject(stateType: TypeVariableName, fields: List<FieldType>) = apply {
    this
      .addType(TypeSpec
        .companionObjectBuilder()
        .addFunction(FunSpec
          .builder("combineReducers")
          .apply {
            fields.forEach { field ->
              addParameter(ParameterSpec
                .builder("${field.name.beginWithLowerCase()}Reducer",
                  ClassName(RXFLUX_CORE, "Reducer")
                    .parameterizedBy(field.type, ACTION_CLASS_NAME)
                )
                .build())
            }
          }
          .addCode(CodeBlock
            .builder()
            .add("return ")
            .addStatement("Reducer<${stateType.name}> { s: ${stateType.name}?, action: %T ->", ACTION_CLASS_NAME)
            .addStatement("val state = s ?: %L(%L = %L())",
              stateType, MAP_FIELD_NAME, IMMUTABLE_HASH_MAP_OF)
            .apply {
              fields.forEach { field ->
                val reducerName = "${field.name.beginWithLowerCase()}Reducer"
                addStatement("val %LOld = state.%L[%S] as? %T",
                  field.name, MAP_FIELD_NAME, field.name, field.type)
                addStatement("val %LNew = %L.reduce(%LOld, action)",
                  field.name, reducerName, field.name)
              }
            }
            .apply {
              add("state")
              fields.forEach { field ->
                add(CodeBlock
                  .builder()
                  .addStatement(".let { if (%LOld !== %LNew) it.set%L(%LNew) else it }",
                    field.name, field.name, field.name.beginWithUpperCase(), field.name)
                  .build())
              }
            }
            .addStatement("}")
            .build())
          .returns(TypeVariableName("Reducer<${stateType.name}, Action>"))
          .build())
        .build())
  }
}
