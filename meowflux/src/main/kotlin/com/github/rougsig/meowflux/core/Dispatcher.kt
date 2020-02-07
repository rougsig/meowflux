package com.github.rougsig.meowflux.core

import kotlinx.coroutines.Job

interface StoreDispatcher {
  fun dispatch(action: Action): Job
}

typealias Dispatcher = suspend (action: Action) -> Unit
