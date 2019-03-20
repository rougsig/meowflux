package com.example.test

import com.example.test.generated.CatFluxState
import com.github.rougsig.rxflux.core.Action
import com.github.rougsig.rxflux.core.Middleware
import com.github.rougsig.rxflux.core.createReducer
import com.github.rougsig.rxflux.core.createStore
import io.reactivex.observers.TestObserver
import junit.framework.TestCase

class CreateStoreTest : TestCase() {

  sealed class CounterAction : Action {
    object Inc : CounterAction()
    object Dec : CounterAction()
  }

  private val reducer = createReducer(CatFluxState(0)) { s: CatFluxState, a: CounterAction ->
    when (a) {
      CounterAction.Inc ->
        s.setCounter(s.counter + 1)
      CounterAction.Dec ->
        s.setCounter(s.counter - 1)
    }
  }

  fun testCreateStore() {
    val store = createStore(reducer)

    store.accept(CounterAction.Inc)
    assertEquals(1, store.state.counter)

    store.accept(CounterAction.Dec)
    assertEquals(0, store.state.counter)
  }

  fun testStoreSubscribe() {
    val store = createStore(reducer)

    val observer = TestObserver<CatFluxState>()
    store.stateLive.subscribe(observer)

    store.accept(CounterAction.Inc)
    store.accept(CounterAction.Dec)

    observer.values()

    observer
      .assertNotComplete()
      .assertValues(
        CatFluxState(1),
        CatFluxState(0)
      )
  }

  fun testStoreMiddleware() {
    val decMiddleware: Middleware<CatFluxState> = { state, next ->
      { action ->
        if (state.invoke()?.counter == 1) {
          next(CounterAction.Dec)
        }
        next(action)
      }
    }
    val store = createStore(reducer, decMiddleware)

    store.accept(CounterAction.Inc)
    assertEquals(1, store.state.counter)
    store.accept(CounterAction.Inc)
    assertEquals(1, store.state.counter)
  }
}
