package com.github.rougsig.rxflux.core

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.functions.Consumer

interface Store<S : Any, A : Action> : Consumer<A> {
  val state: S
  val stateLive: Observable<S>
}

inline fun <S : Any> createStore(
  crossinline reducer: Reducer<S, Action>,
  vararg middleware: Middleware<S>
): Store<S, Action> {
  return object : Store<S, Action> {
    @Volatile
    private var store: S? = null
      set(value) {
        field = value
        value?.let { relay.accept(it) }
      }

    private val relay = PublishRelay.create<S>()

    private val dispatcher = middleware
      .reversed()
      .fold({ a: Action -> dispatch(a) }, { dispatcher, middleware ->
        middleware({ store }, dispatcher)
      })

    override val state: S
      get() = store!!

    override val stateLive: Observable<S>
      get() = relay

    init {
      accept(object : Action {
        override val name: String = "@@INIT"
      })
    }

    override fun accept(action: Action) {
      dispatcher(action)
    }

    private fun dispatch(action: Action) {
      synchronized(this) {
        store = reducer(store, action)
      }
    }
  }
}
