package com.github.rougsig.rxflux2

import com.github.rougsig.rxflux2.counter.CounterModule
import com.github.rougsig.rxflux2.counter.CounterState
import junit.framework.TestCase

class CounterModuleMutationTest : TestCase() {

  fun testIncMutation() {
    val module = CounterModule()

    module.commit { it.inc() }

    assertEquals(CounterState(1), module.state)
  }

  fun testIncByMutation() {
    val module = CounterModule()

    module.commit { it.inc(2) }

    assertEquals(CounterState(2), module.state)
  }

}
