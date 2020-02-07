package com.github.rougsig.meowflux.core

typealias Middleware<S> = (root: Dispatcher, state: () -> S, next: Dispatcher) -> Dispatcher
