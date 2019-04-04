package com.github.rougsig.rxflux.processor.base

import com.github.rougsig.rxflux.core.Action
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import kotlinx.collections.immutable.ImmutableMap

internal val IMMUTABLE_MAP_CLASS_NAME = ImmutableMap::class.asClassName()
internal const val KOTLINX_IMMUTABLE = "kotlinx.collections.immutable"
internal const val RXFLUX_CORE = "com.github.rougsig.rxflux.core"
internal const val RXFLUX_CORE_REDUCER = "createReducer"

internal const val IMMUTABLE_HASH_MAP_OF = "immutableHashMapOf"
internal fun createParameterizedImmutableMap(key: TypeName, value: TypeName): TypeName {
  return IMMUTABLE_MAP_CLASS_NAME.parameterizedBy(key, value)
}

internal val ANY_TYPE_NAME = Any::class.asTypeName()
internal val ANY_NULLABLE_TYPE_NAME = Any::class.asTypeName().copy(nullable = true)

internal val ACTION_CLASS_NAME = Action::class.asClassName()
