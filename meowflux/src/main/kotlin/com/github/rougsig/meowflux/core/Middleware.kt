package com.github.rougsig.meowflux.core

import kotlinx.coroutines.CoroutineScope

typealias Middleware<S> = (
  storeScope: CoroutineScope,
  dispatch: SuspendDispatcher,
  getState: () -> S,
  next: SuspendDispatcher
) -> SuspendDispatcher
