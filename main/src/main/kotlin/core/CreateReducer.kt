package com.github.rougsig.rxflux.core

inline fun <S : Any, reified A : Action> createReducer(
  crossinline initialStateCreator: () -> S,
  crossinline reducer: (S, A) -> S
): Reducer<S, Action> {
  return { s, a ->
    val state = s ?: initialStateCreator()
    if (a is A) {
      reducer(state, a)
    } else {
      state
    }
  }
}
