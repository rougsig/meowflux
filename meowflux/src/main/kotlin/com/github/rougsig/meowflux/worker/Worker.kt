package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action

interface Worker<A : Action, S : Any> {
  suspend fun WorkerContext<S>.work(action: A)
}

@Suppress("FunctionName")
fun <A : Action, S : Any> Worker(
  work: suspend WorkerContext<S>.(action: A) -> Unit
): Worker<A, S> = object : Worker<A, S> {
  override suspend fun WorkerContext<S>.work(action: A) {
    work(action)
  }
}
