package com.github.rougsig.meowflux.core

interface StoreDispatcher {
  fun dispatch(action: Action)
}

typealias Dispatcher = (action: Action) -> Unit
