package com.github.rougsig.meowflux.saga

import com.github.rougsig.meowflux.core.Dispatcher
import com.github.rougsig.meowflux.core.Middleware

fun <S : Any> createSaga(watchers: List<SagaWatcherContext<S>.() -> Unit>): Middleware<S> {
  return { root, state, next ->
    val dispatchers = ArrayList<Dispatcher>(watchers.size)
    val context = SagaWatcherContext(root, state, next) { dispatchers.add(it) }
    watchers.forEach { context.it() }

    val dispatcher: Dispatcher = { action ->
      dispatchers.forEach { it(action) }
    }

    dispatcher
  }
}
