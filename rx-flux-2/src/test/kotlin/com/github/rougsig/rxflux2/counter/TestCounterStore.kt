package com.github.rougsig.rxflux2.counter

import com.github.rougsig.rxflux2.counter.CounterStore.*
import junit.framework.TestCase

class TestCounterStore : TestCase() {
  fun testMutations() {
    val store = CounterStore()

    store.commit(Mutations.increment)

    assertEquals(1, store.state.count)
  }

  fun testMutationsWithParameters() {
    val store = CounterStore()

    store.commit(Mutations.incrementBy, 4)

    assertEquals(4, store.state.count)
  }

  fun testGetters() {
    val store = CounterStore()

    val count0 = store.get(Getters.count)
    store.commit(Mutations.increment)
    val count1 = store.get(Getters.count)

    assertEquals(0, count0)
    assertEquals(1, count1)
  }

  fun testActions() {
    val store = CounterStore()

    store.dispatch(Actions.increment)

    assertEquals(1, store.state.count)
  }
}
