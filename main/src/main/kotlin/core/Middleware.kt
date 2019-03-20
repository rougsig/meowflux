package com.github.rougsig.rxflux.core

interface Middleware<S> {
  fun create(state: () -> S, nextDispatcher: Dispatcher): Dispatcher
}
