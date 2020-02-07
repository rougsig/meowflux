package com.github.rougsig.meowflux.core

typealias Middleware<S> = (nextDispatcher: Dispatcher, store: () -> S, rootDispatcher: Dispatcher) -> Dispatcher
