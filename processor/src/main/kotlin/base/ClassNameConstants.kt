package com.github.rougsig.rxflux.processor.base

import com.github.rougsig.rxflux.core.FluxState
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import kotlinx.collections.immutable.ImmutableMap
import java.lang.reflect.Type

internal val IMMUTABLE_MAP_CLASS_NAME = ImmutableMap::class.asClassName()
internal const val KOTLINX_IMMUTABLE = "kotlinx.collections.immutable"

internal const val IMMUTABLE_HASH_MAP_OF = "immutableHashMapOf"
internal fun createParameterizedImmutableMap(key: TypeName, value: TypeName): TypeName {
  return IMMUTABLE_MAP_CLASS_NAME.parameterizedBy(key, value)
}

internal val FLUX_STATE_CLASS_NAME = FluxState::class.asClassName()
internal fun createParameterizedFluxState(state: TypeName): TypeName {
  return FLUX_STATE_CLASS_NAME.parameterizedBy(state)
}

internal val FLUX_STATE_FIELD_CLASS_NAME = FluxState.Field::class.asClassName()
internal fun createParameterizedFluxStateField(type: TypeName, state: TypeName): TypeName {
  return FLUX_STATE_FIELD_CLASS_NAME.parameterizedBy(type, state)
}

internal val SET_CLASS_NAME = Set::class.asClassName()
internal fun createParameterizedSet(type: TypeName): TypeName {
  return SET_CLASS_NAME.parameterizedBy(type)
}

internal val ANY_TYPE_NAME = Any::class.asTypeName()
internal val ANY_NULLABLE_TYPE_NAME = Any::class.asTypeName().copy(nullable = true)

internal val FLUX_STATE_STATE_CLASS_NAME = FluxState.State::class.asClassName()
internal fun createParameterizedFluxStateState(fieldsType: TypeName, stateType: TypeName): TypeName {
  return FLUX_STATE_STATE_CLASS_NAME.parameterizedBy(fieldsType, stateType)
}
