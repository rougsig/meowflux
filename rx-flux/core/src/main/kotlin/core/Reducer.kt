package com.github.rougsig.rxflux.core

interface Reducer<S, A> {
  fun reduce(state: S?, action: A): S
}

inline fun <S> createReducer(crossinline reducer: (S?, Action) -> S): Reducer<S, Action> {
  return object : Reducer<S, Action> {
    override fun reduce(state: S?, action: Action): S {
      return reducer(state, action)
    }
  }
}

inline fun <S, reified A : Action> safeReducer(
  initialState: S,
  crossinline reducer: (S, A) -> S
): Reducer<S, Action> {
  return createReducer { state, action ->
    val safeState = state ?: initialState
    if (action is A) reducer(safeState, action)
    else safeState
  }
}
