package com.github.rougsig.meowflux.android.demo.store.root

import com.github.rougsig.meowflux.core.Action

data class RootState(
  val count: Int = 0
)

sealed class CounterAction : Action {
  object Increment : CounterAction()
  object Decrement : CounterAction()
}

fun rootReducer(action: Action, previousState: RootState?): RootState {
  val state = previousState ?: RootState()
  return when (action) {
    is CounterAction.Increment -> state.copy(
      count = state.count + 1
    )
    is CounterAction.Decrement -> state.copy(
      count = state.count - 1
    )
    else -> state
  }
}
