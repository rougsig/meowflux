package com.github.rougsig.rxflux2.counter

import com.github.rougsig.rxflux2.core.FluxMutations

class CounterMutations : FluxMutations<CounterState> {

  fun CounterState.inc(incBy: Int = 1): CounterState {
    return copy(count = count + incBy)
  }

  fun CounterState.dec(decBy: Int = 1): CounterState {
    return copy(count = count - decBy)
  }

}
