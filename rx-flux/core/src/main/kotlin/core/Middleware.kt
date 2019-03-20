package com.github.rougsig.rxflux.core

interface Middleware<S> {
  fun create(state: () -> S, nextDispatcher: Dispatcher): Dispatcher
}

inline fun <S> createMiddleware(
  crossinline middleware: (state: () -> S, nextDispatcher: Dispatcher) -> Dispatcher
): Middleware<S> {
  return object : Middleware<S> {
    override fun create(state: () -> S, nextDispatcher: Dispatcher): Dispatcher {
      return middleware(state, nextDispatcher)
    }
  }
}
