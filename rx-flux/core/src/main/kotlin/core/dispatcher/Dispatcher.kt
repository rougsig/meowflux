package com.github.rougsig.rxflux.core.dispatcher

import com.github.rougsig.rxflux.core.action.Action

interface Dispatcher {
  fun dispatch(action: Action)
}

@Suppress("FunctionName")
inline fun Dispatcher(crossinline dispatcher: (action: Action) -> Unit): Dispatcher {
  return object : Dispatcher {
    override fun dispatch(action: Action) {
      dispatcher(action)
    }
  }
}
