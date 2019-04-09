package com.github.rougsig.rxflux.core.reducer

import com.github.rougsig.rxflux.core.action.Action
import kotlin.reflect.KClass

interface Reducer<S, A : Action> {
  fun reduce(state: S?, action: A): S
}

@Suppress("FunctionName")
inline fun <S> Reducer(crossinline reducer: (S?, Action) -> S): Reducer<S, Action> {
  return object : Reducer<S, Action> {
    override fun reduce(state: S?, action: Action): S {
      return reducer(state, action)
    }
  }
}

internal class ReducerImpl<S : Any, A : Action>(
  private val type: KClass<A>,
  private val initialState: S,
  private val mutators: List<Mutator<S, Action>>
) : Reducer<S, Action> {
  override fun reduce(state: S?, action: Action): S {
    val safeState = state ?: initialState
    return if (type.java.isInstance(action)) {
      mutators.fold(safeState) { acc, mutator ->
        mutator.mutate(acc, action)
      }
    } else {
      safeState
    }
  }
}
