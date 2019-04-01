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

inline fun <IS, IA : Action, OS, reified OA : Action> wrapReducer(
  crossinline reducer: (OS, OA) -> OS,
  crossinline mapChildState: (IS) -> OS,
  crossinline mapParentState: (IS, OS) -> IS
): (IS, IA) -> IS {
  return { s: IS, a: IA ->
    if (a is OA) {
      mapParentState(s, reducer.invoke(mapChildState(s), a))
    } else {
      s
    }
  }
}
