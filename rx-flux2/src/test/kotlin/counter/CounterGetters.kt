package com.github.rougsig.rxflux2.counter

import com.github.rougsig.rxflux2.core.FluxGetters

interface CounterGetters : FluxGetters<CounterState> {

  val isPositiveCount
    get() = state.count > 0

}

