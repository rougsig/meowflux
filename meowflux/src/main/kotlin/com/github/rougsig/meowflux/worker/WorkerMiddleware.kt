package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Middleware
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
fun <S : Any> createWorkerMiddleware(
  rootWorker: Worker<S>
): Middleware<S> = middleware@{ storeScope, dispatch, getState, next ->
  val context = WorkerContext(dispatch, getState)
  val channel = BroadcastChannel<Action>(128)
  val flow = channel.asFlow()

  storeScope.launch { rootWorker(context, flow) }

  return@middleware { action ->
    next(action)
    require(channel.offer(action)) { "workers action channel overflow" }
  }
}
