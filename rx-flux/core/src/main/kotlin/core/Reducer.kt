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

inline fun <S, reified A : Action> combineReducers(
  initialState: S,
  vararg reducer: (S, A) -> S
): Reducer<S, A> {
  return createReducer<S, A>(initialState) { ps, a ->
    reducer.fold(ps) { s, r -> r.invoke(s, a) }
  }
}
