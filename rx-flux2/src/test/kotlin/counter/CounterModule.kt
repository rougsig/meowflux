package com.github.rougsig.rxflux2.counter

import com.github.rougsig.rxflux2.core.FluxModule

class CounterModule : FluxModule<CounterState, CounterMutations, CounterGetters, CounterActions>(
  initialState = CounterState(0),
  mutations = CounterMutations(),
  getters = CounterGetters(),
  actions = CounterActions()
)
