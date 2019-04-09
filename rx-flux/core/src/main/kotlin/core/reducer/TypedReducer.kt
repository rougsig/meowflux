package com.github.rougsig.rxflux.core.reducer

import com.github.rougsig.rxflux.core.action.Action
import kotlin.reflect.KClass

abstract class TypedReducer<S : Any, A : Action>(
  private val initialState: S,
  val type: KClass<out Action>
) : Reducer<S, Action> {
  @Suppress("UNCHECKED_CAST")
  final override fun reduce(state: S?, action: Action): S {
    val safeState = state ?: initialState
    return if (type.java.isInstance(action)) {
      reduceTyped(safeState, action as A)
    } else {
      safeState
    }
  }

  abstract fun reduceTyped(state: S, action: A): S
}
