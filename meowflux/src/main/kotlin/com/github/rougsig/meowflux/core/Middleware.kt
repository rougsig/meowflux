package com.github.rougsig.meowflux.core

typealias Middleware<S> = (dispatch: Dispatcher, state: () -> S, next: Dispatcher) -> Dispatcher
