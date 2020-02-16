package com.github.rougsig.meowflux.core

import kotlinx.coroutines.CoroutineScope

typealias Middleware<S> = (
  storeScope: CoroutineScope,
  dispatch: Dispatcher,
  getState: () -> S,
  next: Dispatcher
) -> Dispatcher
