package com.github.rougsig.rxflux.core

typealias Middleware<S> = (store: () -> S?, nextDispatcher: Dispatcher) -> Dispatcher
