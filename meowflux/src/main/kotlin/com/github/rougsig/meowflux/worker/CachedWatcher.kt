package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

interface CachedAction : Action {
  val skipCache: Boolean
}

fun <S : Any, A : CachedAction> cachedWatcher(
  worker: Worker<A, S>,
  select: suspend Flow<Action>.(context: WorkerContext<S>) -> Flow<A>,
  expireIn: Duration = Duration.ofMinutes(5L)
): Watcher<A, S> = object : Watcher<A, S> {
  var validUntil: LocalDateTime? = null

  override suspend fun Flow<Action>.watch(context: WorkerContext<S>) {
    this
      .mapNotNull { it as? CachedAction }
      .filter { validUntil == null || validUntil!!.plus(expireIn).isBefore(LocalDateTime.now()) || it.skipCache }
      .select(context)
      .onEach { validUntil = LocalDateTime.now() }
      .applyWorker(context, worker)
  }
}
