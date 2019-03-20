package com.github.rougsig.rxflux.core

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

interface Store<S : Any> : Dispatcher {
  val state: S
  val stateLive: Observable<S>
}

inline fun <S : Any> createStore(
  crossinline reducer: Reducer<S, Action>,
  vararg middleware: Middleware<S>,
  initialState: S? = null
  ): Store<S> {
  return object : Store<S> {
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
      dispatchAction(object : Action {
        override val name: String = "@@INIT"
      })

      dispatcher = middleware.fold(createDispatcher { a: Action -> dispatchAction(a) }) { dispatcher, middleware ->
        middleware.create({ state }, dispatcher)
      }
    }

    override fun dispatch(action: Action) {
      dispatcher.dispatch(action)
    }

    private fun dispatchAction(action: Action) {
      synchronized(this) {
        store = reducer(store, action)
      }
    }
  }
}
