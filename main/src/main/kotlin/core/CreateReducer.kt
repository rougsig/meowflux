package com.github.rougsig.rxflux.core

inline fun <S, reified A : Action> createReducer(
  initialState: S,
  crossinline reducer: (S, A) -> S
): Reducer<S, Action> {
  return { s, a ->
    val state = s ?: initialState
    if (a is A) {
      reducer(state, a)
    } else {
      state
    }
  }
}
