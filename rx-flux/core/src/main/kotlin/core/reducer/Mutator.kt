package com.github.rougsig.rxflux.core.reducer

import com.github.rougsig.rxflux.core.action.Action
import kotlin.reflect.KClass

interface Mutator<S, A: Action> {
  fun mutate(state: S, action: A): S
}

internal class MutatorImpl<S : Any, A : Action>(
  private val type: KClass<A>,
  private val mutator: (state: S, action: A) -> S
) : Mutator<S, Action> {
  override fun mutate(state: S, action: Action): S {
    return if (type.java.isInstance(action)) {
      mutator(state, action as A)
    } else {
      state
    }
  }
}
