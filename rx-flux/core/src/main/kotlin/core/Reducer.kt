package com.github.rougsig.rxflux.core

typealias Reducer<S, A> = (state: S?, action: A) -> S

inline fun <S, reified A : Action> createReducer(
  initialState: S,
  crossinline reducer: (S, A) -> S
): Reducer<S, Action> {
  return { state: S?, action: Action ->
    val safeState = state ?: initialState
    if (action is A) {
      reducer(safeState, action)
    } else {
      safeState
    }
  }
}
