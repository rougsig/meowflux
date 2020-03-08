package com.github.rougsig.meowflux.story

import com.github.rougsig.meowflux.core.Action
import kotlinx.coroutines.flow.Flow

typealias Story<S, D> = (
  actions: Flow<Action>,
  state: StateProvider<S>,
  dependencies: D
) -> Flow<Action>
