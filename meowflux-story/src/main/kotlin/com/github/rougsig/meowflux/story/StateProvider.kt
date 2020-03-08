package com.github.rougsig.meowflux.story

import kotlinx.coroutines.flow.Flow

class StateProvider<S : Any>(
  private val stateFlow: Flow<S>,
  private val getState: () -> S
) : Flow<S> by stateFlow {
  val value: S
    get() = getState()
}
