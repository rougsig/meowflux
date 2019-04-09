package com.github.rougsig.rxflux.core.middleware

import com.github.rougsig.rxflux.core.dispatcher.Dispatcher
import com.github.rougsig.rxflux.core.store.StateAccessor

interface Middleware<S : Any> {
  fun apply(accessor: StateAccessor<S>, dispatcher: Dispatcher): (Dispatcher) -> Dispatcher
}

@Suppress("FunctionName")
inline fun <S : Any> Middleware(
  crossinline middleware: (accessor: StateAccessor<S>, dispatcher: Dispatcher) -> (next: Dispatcher) -> Dispatcher
): Middleware<S> {
  return object : Middleware<S> {
    override fun apply(accessor: StateAccessor<S>, dispatcher: Dispatcher): (Dispatcher) -> Dispatcher {
      return middleware(accessor, dispatcher)
    }
  }
}
