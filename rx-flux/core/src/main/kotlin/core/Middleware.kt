package com.github.rougsig.rxflux.core

interface Middleware<S : Any> {
  fun create(state: () -> S, nextDispatcher: Dispatcher): Dispatcher
}

inline fun <S : Any> createMiddleware(
  crossinline middleware: (state: () -> S, nextDispatcher: Dispatcher) -> Dispatcher
): Middleware<S> {
  return object : Middleware<S> {
    override fun create(state: () -> S, nextDispatcher: Dispatcher): Dispatcher {
      return middleware(state, nextDispatcher)
    }
  }
}

inline fun <I : Any, O : Any> wrapMiddleware(
  middleware: Middleware<O>,
  crossinline mapper: (I) -> O
): Middleware<I> {
  return object : Middleware<I> {
    override fun create(state: () -> I, nextDispatcher: Dispatcher): Dispatcher {
      return middleware.create({ mapper(state()) }, nextDispatcher)
    }
  }
}
