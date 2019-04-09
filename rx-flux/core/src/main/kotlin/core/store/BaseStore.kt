package com.github.rougsig.rxflux.core.store

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.action.InitAction
import com.github.rougsig.rxflux.core.dispatcher.Dispatcher
import com.github.rougsig.rxflux.core.middleware.Middleware
import com.github.rougsig.rxflux.core.reducer.Reducer
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

abstract class BaseStore<S : Any>(
  private val reducer: Reducer<S, Action>,
  vararg middleware: Middleware<S>,
  initialState: S? = null
) : Store<S> {
  @Volatile
  private var store: S? = initialState
    set(value) {
      field = value
      value?.let { relay.accept(it) }
    }

  private val dispatcher: Dispatcher
  private val relay = PublishRelay.create<S>()

  override val state: S
    get() = store!!

  override val stateLive: Observable<S>
    get() = relay

  init {
    dispatchAction(InitAction)

    dispatcher = middleware
      .reversed()
      .fold(Dispatcher { a: Action -> dispatchAction(a) }) { dispatcher, middleware ->
        middleware.apply(StateAccessor { state }, Dispatcher { a: Action -> dispatch(a) })(dispatcher)
      }
  }

  override fun dispatch(action: Action) {
    dispatcher.dispatch(action)
  }

  private fun dispatchAction(action: Action) {
    synchronized(this) {
      val newStore = reducer.reduce(store, action)
      if (newStore !== store) {
        store = newStore
      }
    }
  }
}
