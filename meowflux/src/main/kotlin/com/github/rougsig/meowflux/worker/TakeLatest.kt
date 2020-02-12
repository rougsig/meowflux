package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

inline fun <reified A : Action, S : Any> takeLatest(
  timeoutMillis: Long,
  crossinline worker: suspend WorkerContext<S>.(action: A) -> Unit
) = createWorker<S> { context, next ->
  var job: Job? = null
  { action ->
    if (action is A) {
      job?.cancel()
      coroutineScope {
        job = async {
          delay(timeoutMillis)
          context.worker(action)
        }
      }
    } else {
      next(action)
    }
  }
}
