package com.github.rougsig.rxflux.core

interface Middleware<S> {
  fun apply(getState: () -> S, dispatcher: Dispatcher): (Dispatcher) -> Dispatcher
}

inline fun <S> createMiddleware(crossinline middleware: (getState: () -> S, dispatcher: Dispatcher) -> (next: Dispatcher) -> Dispatcher): Middleware<S> {
  return object : Middleware<S> {
    override fun apply(getState: () -> S, dispatcher: Dispatcher): (Dispatcher) -> Dispatcher {
      return middleware(getState, dispatcher)
    }
  }
}

inline fun <I : Any, O : Any> wrapMiddleware(
  middleware: Middleware<O>,
  crossinline mapState: (I) -> O
): Middleware<I> {
  return createMiddleware { getState, next -> middleware.apply({ mapState(getState()) }, next) }
}
