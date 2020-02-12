package com.github.rougsig.meowflux.core

interface Store<S : Any> : StoreDispatcher {
  fun getState(): S
  fun subscribe(listener: (S) -> Unit)
  fun unsubscribe()
}
