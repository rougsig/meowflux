package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import kotlinx.coroutines.flow.Flow

interface Watcher<A : Action, S : Any> {
  suspend fun Flow<Action>.watch(context: WorkerContext<S>)
}

fun <S : Any, A : Action> watcher(
  worker: Worker<A, S>,
  select: suspend Flow<Action>.(context: WorkerContext<S>) -> Flow<A>
): Watcher<A, S> = object : Watcher<A, S> {
  override suspend fun Flow<Action>.watch(context: WorkerContext<S>) {
    this
      .select(context)
      .applyWorker(context, worker)
  }
}
