package com.github.rougsig.rxflux.counter

import com.github.rougsig.rxflux.dsl.ConfigurableReducer

private sealed class Action

private data class Inc(val incBy: Int) : Action()
private data class Dec(val decBy: Int) : Action()

class CounterReducer(
  override val prefix: String
) : ConfigurableReducer<CounterState>(
  initialState = CounterState(0)
) {
  init {
    mutator(Inc::class) { state, action ->
      state.copy(count = state.count + action.incBy)
    }

    mutator(Dec::class) { state, action ->
      state.copy(count = state.count - action.decBy)
    }
  }

  fun inc(incBy: Int = 1) = createAction { Inc(incBy) }
  fun dec(decBy: Int = 1) = createAction { Dec(decBy) }
}
