package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action

interface Worker<A : Action, S : Any> {
  suspend fun WorkerContext<S>.work(action: A)
}

fun <A : Action, S : Any> worker(
  work: suspend WorkerContext<S>.(action: A) -> Unit
): Worker<A, S> = object : Worker<A, S> {
  override suspend fun WorkerContext<S>.work(action: A) {
    work(action)
  }
}

