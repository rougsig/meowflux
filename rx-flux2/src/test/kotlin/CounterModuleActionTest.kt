package com.github.rougsig.rxflux2

import com.github.rougsig.rxflux2.counter.CounterModule
import junit.framework.TestCase

class CounterModuleActionTest : TestCase() {

  fun testIncAction() {
    val module = CounterModule()

    module.dispatch { inc(it) }

    assertEquals(1, module.state.count)
  }

}
