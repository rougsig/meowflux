package com.github.rougsig.rxflux.dsl

import com.github.rougsig.rxflux.core.reducer.Mutator
import com.github.rougsig.rxflux.core.reducer.MutatorImpl
import com.github.rougsig.rxflux.core.reducer.Reducer
import kotlin.reflect.KClass

abstract class ConfigurableReducer<S : Any>(
  initialState: S
) : Reducer<S>(initialState) {
  private val _mutators = mutableListOf<Mutator<S, Any>>()
  override val mutators: List<Mutator<S, Any>> = _mutators

  fun <A : Any> mutator(
    type: KClass<A>,
    mutator: (S, A) -> S
  ): ConfigurableReducer<S> = apply {
    _mutators.add(MutatorImpl(type, mutator))
  }
}
