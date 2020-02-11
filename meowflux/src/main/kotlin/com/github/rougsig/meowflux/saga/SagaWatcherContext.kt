package com.github.rougsig.meowflux.saga

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Dispatcher

class SagaWatcherContext<S : Any>(
  private val root: Dispatcher,
  private val getState: () -> S,
  private val next: Dispatcher,
  private val addDispatcher: (Dispatcher) -> Unit
) {
  private val workerContext = SagaWorkerContext(root, getState)

  fun createWorker(worker: suspend SagaWorkerContext<S>.(
    action: Action,
    root: Dispatcher,
    state: () -> S,
    next: Dispatcher
  ) -> Unit) = addDispatcher { action ->
    workerContext.worker(action, root, getState, next)
  }
}
