package com.example.test

import com.example.test.generated.CatFluxState
import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.dispatcher.Dispatcher
import com.github.rougsig.rxflux.core.middleware.Middleware
import com.github.rougsig.rxflux.core.store.Store
import com.github.rougsig.rxflux.dsl.ConfigurableReducer
import io.reactivex.observers.TestObserver
import junit.framework.TestCase

class CreateStoreTest : TestCase() {

  sealed class CounterAction : Action() {
    object Inc : CounterAction()
    object Dec : CounterAction()
  }

  class CounterReducer : ConfigurableReducer<CatFluxState, CounterAction>(
    CounterAction::class,
    CatFluxState(0)
  ) {
    init {
      mutator(CounterAction.Inc::class) { s, _ -> s.setCounter(s.counter + 1) }
      mutator(CounterAction.Dec::class) { s, _ -> s.setCounter(s.counter - 1) }
    }
  }

  private val reducer = CounterReducer()

  fun testCreateStore() {
    val store = Store(reducer)

    store.dispatch(CounterAction.Inc)
    assertEquals(1, store.state.counter)

    store.dispatch(CounterAction.Dec)
    assertEquals(0, store.state.counter)
  }

  fun testStoreSubscribe() {
    val store = Store(reducer)

    val observer = TestObserver<CatFluxState>()
    store.stateLive.subscribe(observer)

    store.dispatch(CounterAction.Inc)
    store.dispatch(CounterAction.Dec)

    observer.values()

    observer
      .assertNotComplete()
      .assertValues(
        CatFluxState(1),
        CatFluxState(0)
      )
  }

  fun testStoreMiddleware() {
    val decMiddleware: Middleware<CatFluxState> = Middleware { accessor, _ ->
      { next ->
        Dispatcher { action ->
          if (accessor.getState().counter == 1) next.dispatch(CounterAction.Dec)
          next.dispatch(action)
        }
      }
    }
    val store = Store(reducer, decMiddleware)

    store.dispatch(CounterAction.Inc)
    assertEquals(1, store.state.counter)
    store.dispatch(CounterAction.Inc)
    assertEquals(1, store.state.counter)
  }
}
