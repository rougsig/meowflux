package com.github.rougsig.rxflux

import com.github.rougsig.rxflux.core.store.Store
import com.github.rougsig.rxflux.counter.CounterReducer
import com.github.rougsig.rxflux.counter.CounterState
import junit.framework.TestCase

class CounterReducerTest : TestCase() {

  fun testInc() {
    val store = Store()
    val counterReducer = CounterReducer("counter")
    store.addReducer(counterReducer)

    store.dispatch(counterReducer.inc())

    assertEquals(CounterState(1), counterReducer.state)
  }

  fun testIncBy() {
    val store = Store()
    val counterReducer = CounterReducer("counter")
    store.addReducer(counterReducer)

    store.dispatch(counterReducer.inc(2))

    assertEquals(CounterState(2), counterReducer.state)
  }

  fun testMultipleCounterReducers() {
    val store = Store()
    val catReducer = CounterReducer("cat/counter")
    val duckReducer = CounterReducer("duck/counter")
    store.addReducer(catReducer)
    store.addReducer(duckReducer)

    store.dispatch(catReducer.inc())
    store.dispatch(duckReducer.inc())

    assertEquals(CounterState(1), catReducer.state)
    assertEquals(CounterState(1), duckReducer.state)
  }

}
