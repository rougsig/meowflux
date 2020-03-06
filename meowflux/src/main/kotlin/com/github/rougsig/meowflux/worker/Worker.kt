package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import kotlinx.coroutines.flow.Flow

typealias Worker<S> = suspend WorkerContext<S>.(actions: Flow<Action>) -> Unit
