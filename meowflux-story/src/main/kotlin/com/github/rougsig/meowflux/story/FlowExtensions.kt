package com.github.rougsig.meowflux.story

import com.github.rougsig.meowflux.core.Action
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

inline fun <reified A : Action> Flow<Action>.typeOf(): Flow<A> {
  return this.mapNotNull { it as? A }
}
