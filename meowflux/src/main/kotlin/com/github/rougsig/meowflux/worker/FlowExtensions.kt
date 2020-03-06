package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

inline fun <reified A : Any> Flow<Action>.typeOf(): Flow<A> {
  return mapNotNull { it as? A }
}
