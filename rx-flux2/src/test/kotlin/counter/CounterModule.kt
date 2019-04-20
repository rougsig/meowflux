package com.github.rougsig.rxflux2.counter

import com.github.rougsig.rxflux2.core.FluxModule

class CounterModule :
  FluxModule<CounterState, CounterMutations, CounterModule, CounterActions>(CounterState(0)),
  CounterGetters {

  override val mutations = CounterMutations()
  override val actions = CounterActions()
  override val getters
    get() = this
}
