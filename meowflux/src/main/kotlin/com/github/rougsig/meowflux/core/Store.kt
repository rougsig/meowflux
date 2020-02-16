package com.github.rougsig.meowflux.core

import kotlinx.coroutines.flow.Flow

interface Store<S : Any> : StoreDispatcher {
  fun getState(): S
  val stateFlow: Flow<S>
}
