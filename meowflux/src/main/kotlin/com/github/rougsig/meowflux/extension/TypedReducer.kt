package com.github.rougsig.meowflux.extension

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Reducer

inline fun <reified A : Action, S : Any> createTypedReducer(
  initialState: S,
  crossinline reducer: (action: A, previousState: S) -> S
): Reducer<S> = { action, previousState ->
  val state = previousState ?: initialState
  if (action is A) reducer(action, state)
  else state
}
