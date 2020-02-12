package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Dispatcher
import com.github.rougsig.meowflux.core.Middleware
import com.github.rougsig.meowflux.core.createStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

inline fun <reified A : Action, S : Any> takeLatest(
  timeoutMillis: Long,
  crossinline worker: suspend WorkerContext<S>.(action: A) -> Unit
): Worker<S> = { context, next ->
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
