package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

suspend fun <A : Action, S : Any> Flow<A>.applyWorker(context: WorkerContext<S>, worker: Worker<A, S>) {
  collect { action ->
    with(worker) {
      context.work(action)
    }
  }
}
