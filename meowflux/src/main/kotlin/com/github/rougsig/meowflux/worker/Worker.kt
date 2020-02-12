package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Dispatcher

typealias Worker<S> = suspend (context: WorkerContext<S>, next: Dispatcher) -> Dispatcher
