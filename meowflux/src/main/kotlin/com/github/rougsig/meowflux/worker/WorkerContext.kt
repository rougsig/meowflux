package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Dispatcher

class WorkerContext<S : Any>(
  internal val dispatch: Dispatcher,
  internal val getState: () -> S
) {
  val state: S get() = getState()
  fun put(action: Action) = dispatch(action)
}
