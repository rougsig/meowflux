package com.github.rougsig.meowflux.core

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface Store<S : Any> {
  val state: S
  val stateFlow: Flow<S>
  fun dispatch(action: Action): Job
}
