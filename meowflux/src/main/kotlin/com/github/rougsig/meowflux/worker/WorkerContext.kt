package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Dispatcher

data class WorkerContext<S : Any>(
  private val dispatcher: Dispatcher,
  private val state: () -> S
) {
  suspend fun <T> select(selector: (S) -> T): T {
    return selector(state())
  }

  suspend fun put(action: Action) {
    dispatcher(action)
  }
}
