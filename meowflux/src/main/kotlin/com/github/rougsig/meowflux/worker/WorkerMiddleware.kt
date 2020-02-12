package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Dispatcher
import com.github.rougsig.meowflux.core.Middleware

class WorkerMiddleware<S : Any>(vararg workers: Worker<S>) : Middleware<S> {
  private val workers = workers.toList()

  override fun invoke(dispatch: Dispatcher, getState: () -> S, next: Dispatcher): Dispatcher {
    val context = WorkerContext(dispatch, getState)
    return { action ->
      workers.fold(next) { acc, worker ->
        worker(context, acc)
      }(action)
    }
  }
}
