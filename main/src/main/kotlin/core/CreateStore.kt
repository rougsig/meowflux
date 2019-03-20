package com.github.rougsig.rxflux.core

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.functions.Consumer

interface Store<S : Any, A : Action> : Consumer<A>, ObservableSource<S> {
  val state: S
}

inline fun <S : Any> createStore(
  crossinline reducer: Reducer<S, Action>,
  vararg middleware: Middleware<S>
): Store<S, Action> {
  return object : Store<S, Action> {
    private val relay = PublishRelay.create<S>()
    @Volatile
    private var store: S? = null
      set(value) {
        field = value
        value?.let { relay.accept(it) }
      }

    override val state: S
      get() = store!!

    private val dispatcher = middleware
      .reversed()
      .fold({ a: Action -> dispatch(a) }, { dispatcher, middleware ->
        middleware({ store }, dispatcher)
      })

    init {
      accept(object : Action {
        override val name: String = "@@INIT"
      })
    }

    override fun accept(action: Action) {
      dispatcher(action)
    }

    override fun subscribe(observer: Observer<in S>) {
      relay.subscribe(observer)
    }

    private fun dispatch(action: Action) {
      synchronized(this) {
        store = reducer(store, action)
      }
    }
  }
}
