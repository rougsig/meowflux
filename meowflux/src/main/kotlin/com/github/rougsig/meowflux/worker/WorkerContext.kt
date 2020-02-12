package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Dispatcher

class WorkerContext<S : Any>(
  private val dispatch: Dispatcher,
  private val getState: () -> S
) {
  suspend fun put(action: Action) {
    dispatch(action)
  }

  suspend fun <T> select(selector: (S) -> T): T {
    return selector(getState())
  }
}
