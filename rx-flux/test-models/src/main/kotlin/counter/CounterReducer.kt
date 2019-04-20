package com.example.test.counter

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.dsl.ConfigurableReducer

private sealed class ReducerAction : Action()
private object IncAction : ReducerAction()
private object DecAction : ReducerAction()

class CounterReducer(
  namespace: String = ""
) : ConfigurableReducer<CounterState>(
  type = Action::class,
  initialState = CounterState(0),
  namespace = namespace
) {
  init {
    mutator(IncAction::class) { state: CounterState, _ ->
      state.copy(count = state.count + 1)
    }

    mutator(DecAction::class) { state: CounterState, _ ->
      state.copy(count = state.count - 1)
    }
  }

  fun inc() = createAction { IncAction }

  fun dec() = createAction { DecAction }
}
