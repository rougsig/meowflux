package com.github.rougsig.rxflux.processor.stategenerator

import com.github.rougsig.rxflux.processor.base.Generator
import com.github.rougsig.rxflux.processor.base.IMMUTABLE_HASH_MAP_OF
import com.github.rougsig.rxflux.processor.base.KOTLINX_IMMUTABLE
import com.github.rougsig.rxflux.processor.base.createParameterizedImmutableMap
import com.github.rougsig.rxflux.processor.extensions.beginWithUpperCase
import com.squareup.kotlinpoet.*

internal val stateGenerator = StateGenerator()

private const val MAP_FIELD_NAME = "map"

internal class StateGenerator : Generator<StateType> {
  override fun generateFile(type: StateType): FileSpec {
    return FileSpec
      .builder(type.packageName, type.stateName)
      .addImport(KOTLINX_IMMUTABLE, IMMUTABLE_HASH_MAP_OF)
      .addType(TypeSpec
        .classBuilder(type.stateName)
        .addModifiers(KModifier.INTERNAL)
        .addConstructor(type.fields)
        .addFields(type.stateName, type.fields)
        .addToString()
        .build())
      .build()
  }

  private fun TypeSpec.Builder.addConstructor(fields: List<FieldType>) = apply {
    val anyType = Any::class.asTypeName()
    val anyNullableType = Any::class.asTypeName().copy(nullable = true)
    val mapType = createParameterizedImmutableMap(anyType, anyNullableType)

    this
      .primaryConstructor(FunSpec
        .constructorBuilder()
        .addParameter(MAP_FIELD_NAME, mapType)
        .build())
      .addProperty(PropertySpec
        .builder(MAP_FIELD_NAME, mapType)
        .initializer(MAP_FIELD_NAME)
        .build())
      .addFunction(FunSpec
        .constructorBuilder()
        .addParameters(fields.map { ParameterSpec.builder(it.name, it.type).build() })
        .callThisConstructor(CodeBlock.of(
          "%L = %L(${fields.joinToString(",") { "%S to %L" }})",
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
}
