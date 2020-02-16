package com.github.rougsig.meowflux.worker

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Dispatcher
import com.github.rougsig.meowflux.core.Middleware
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class WorkerMiddleware<S : Any>(private val watchers: List<Watcher<*, S>>) : Middleware<S> {
  override fun invoke(storeScope: CoroutineScope, dispatch: Dispatcher, getState: () -> S, next: Dispatcher): Dispatcher {
    val context = WorkerContext(dispatch, getState)
    val channel = BroadcastChannel<Action>(128)
    val flow = channel.asFlow()

    watchers.forEach { watcher ->
      storeScope.launch {
        watcher.apply {
          flow.watch(context)
        }
      }
    }

    return { action ->
      require(channel.offer(action)) {
        "workers action channel overflow"
      }
      next(action)
    }
  }
}
