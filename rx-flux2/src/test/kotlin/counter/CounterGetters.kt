package com.github.rougsig.rxflux2.counter

import com.github.rougsig.rxflux2.core.FluxGetters

class CounterGetters(
  override val previousState: () -> CounterState?,
  override val state: () -> CounterState
) : FluxGetters<CounterState>() {
  val isPositiveCount by Getter { count > 0 }
}

