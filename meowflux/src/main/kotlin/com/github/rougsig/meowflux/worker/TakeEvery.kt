package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Dispatcher
import com.github.rougsig.meowflux.core.Middleware

inline fun <reified A : Action, S : Any> takeEvery(
  crossinline worker: suspend WorkerContext<S>.(action: A) -> Unit
) = createWorker<S> { context, next ->
  { action ->
    if (action is A) context.worker(action)
    else next(action)
  }
}
