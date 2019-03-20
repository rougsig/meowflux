package com.github.rougsig.rxflux.core

interface Dispatcher {
  fun dispatch(action: Action)
}

inline fun createDispatcher(crossinline dispatcher: (action: Action) -> Unit): Dispatcher {
  return object : Dispatcher {
    override fun dispatch(action: Action) {
      return dispatcher(action)
    }
  }
}
