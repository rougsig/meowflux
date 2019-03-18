package com.github.rougsig.rxflux.processor.base

import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import kotlinx.collections.immutable.ImmutableMap

internal val IMMUTABLE_MAP_CLASS_NAME = ImmutableMap::class.asClassName()
internal const val KOTLINX_IMMUTABLE = "kotlinx.collections.immutable"
internal const val IMMUTABLE_HASH_MAP_OF = "immutableHashMapOf"
internal fun createParameterizedImmutableMap(key: TypeName, value: TypeName): TypeName {
  return IMMUTABLE_MAP_CLASS_NAME.parameterizedBy(key, value)
}
