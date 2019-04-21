package com.github.rougsig.rxflux2.counter

import com.github.rougsig.rxflux2.core.FluxModule

class CounterModule :
  FluxModule<CounterState, CounterMutations, CounterGetters, CounterActions>(CounterState(0)) {
  override val mutations = CounterMutations()
  override val getters = CounterGetters(::previousState, ::state)
  override val actions = CounterActions(mutations, getters)
}
