package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

interface CachedAction : Action {
  val skipCache: Boolean
}

inline fun <reified A : CachedAction, S : Any> cached(
  expireIn: Duration = Duration.ofMinutes(5),
  worker: Worker<S>
) = createWorker<S> { context, next ->
  var validUntil: LocalDateTime? = null
  { action ->
    val isExpired = validUntil == null || validUntil!!.plus(expireIn).isBefore(LocalDateTime.now())
    if (action is A && (action.skipCache || isExpired)) {
      worker.work(context, next)
      validUntil = LocalDateTime.now()
    } else next(action)
  }
}
