package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Dispatcher

interface Worker<S : Any> {
  suspend fun work(context: WorkerContext<S>, next: Dispatcher): Dispatcher
}

inline fun <S : Any> createWorker(
  crossinline worker: (context: WorkerContext<S>, next: Dispatcher) -> Dispatcher
): Worker<S> = object : Worker<S> {
  override suspend fun work(context: WorkerContext<S>, next: Dispatcher): Dispatcher {
    return worker(context, next)
  }
}
