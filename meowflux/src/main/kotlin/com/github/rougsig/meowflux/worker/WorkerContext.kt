package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.SuspendDispatcher

class WorkerContext<S : Any>(
  internal val dispatch: SuspendDispatcher,
  internal val getState: () -> S
) {
  val state: S get() = getState()
  suspend fun put(action: Action) = dispatch(action)
}
