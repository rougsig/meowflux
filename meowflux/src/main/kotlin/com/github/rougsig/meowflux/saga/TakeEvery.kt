package com.github.rougsig.meowflux.saga

import com.github.rougsig.meowflux.core.Action

inline fun <reified A : Action, S : Any> SagaWatcherContext<S>.takeEvery(
  crossinline worker: suspend SagaWorkerContext<S>.(action: A) -> Unit
) = createWorker { action, _, _, next ->
  if (action is A) worker(action)
  else next(action)
}
