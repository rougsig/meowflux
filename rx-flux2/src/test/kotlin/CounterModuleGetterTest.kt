package com.github.rougsig.rxflux2

import com.github.rougsig.rxflux2.counter.CounterModule
import junit.framework.TestCase

class CounterModuleGetterTest : TestCase() {

  fun testisPositiveCountGetter() {
    val module = CounterModule()

    module.commit { it.dec(10) }

    assertEquals(false, module.isPositiveCount)
  }

}
