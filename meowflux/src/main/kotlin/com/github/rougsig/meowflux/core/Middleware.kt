package com.github.rougsig.meowflux.core

typealias Middleware<S> = (dispatch: Dispatcher, getState: () -> S) -> (next: Dispatcher) -> Dispatcher
