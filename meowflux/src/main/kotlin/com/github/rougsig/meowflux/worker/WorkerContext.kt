package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Dispatcher

class WorkerContext<S : Any>(
  internal val dispatcher: Dispatcher,
  internal val getState: () -> S
) {
  val state: S
    get() = getState()

  suspend fun put(action: Action) {
    dispatcher(action)
  }
}
