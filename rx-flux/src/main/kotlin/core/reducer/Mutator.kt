package com.github.rougsig.rxflux.core.reducer

import kotlin.reflect.KClass

interface Mutator<S, A : Any> {
  fun mutate(state: S, action: A): S
}

internal class MutatorImpl<S : Any, A : Any>(
  private val type: KClass<A>,
  private val mutator: (state: S, action: A) -> S
) : Mutator<S, Any> {
  override fun mutate(state: S, action: Any): S {
    return if (type.java.isInstance(action)) {
      mutator(state, type.java.cast(action))
    } else {
      state
    }
  }
}
