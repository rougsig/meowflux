package com.github.rougsig.rxflux.core

typealias Middleware<S> = (() -> S, Dispatcher) -> (Dispatcher) -> Dispatcher

inline fun <I : Any, O : Any> wrapMiddleware(
  crossinline middleware: Middleware<O>,
  crossinline getState: (I) -> O
): Middleware<I> {
  return { getState, next -> middleware({ getState(getState()) }, next) }
}
