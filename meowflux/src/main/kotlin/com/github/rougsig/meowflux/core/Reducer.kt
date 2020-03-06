package com.github.rougsig.meowflux.core

typealias Reducer<S> = (action: Action, previousState: S?) -> S

@Suppress("FunctionName")
inline fun <reified A : Action, S : Any> TypedReducer(
  initialState: S,
  crossinline reducer: (action: A, previousState: S) -> S
): Reducer<S> = { action, previousState ->
  val state = previousState ?: initialState
  if (action is A) reducer(action, state)
  else state
}
