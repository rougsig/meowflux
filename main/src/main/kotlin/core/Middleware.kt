package com.github.rougsig.rxflux.core

typealias Dispatcher = (Action) -> Unit
typealias Middleware<S> = (Dispatcher, () -> S?) -> (Dispatcher) -> Dispatcher
