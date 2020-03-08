package com.github.rougsig.meowflux.story

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Dispatcher
import com.github.rougsig.meowflux.core.Middleware
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class StoryMiddleware<S : Any, D : Any>(
  private val rootStory: Story<S, D>,
  private val dependencies: D
) : Middleware<S> {
  override fun invoke(
    storeScope: CoroutineScope,
    dispatch: Dispatcher,
    getState: () -> S,
    next: Dispatcher
  ): Dispatcher {
    val actions = BroadcastChannel<Action>(128)
    val states = ConflatedBroadcastChannel<S>()
    val stateProvider = StateProvider(states.asFlow(), getState)

    storeScope.launch {
      rootStory(actions.asFlow(), stateProvider, dependencies)
        .collect { dispatch(it) }
    }

    return { action ->
      next(action)
      states.offer(getState())
      require(actions.offer(action)) {
        "story middleware actions channel overflow. Check for infinite actions loop."
      }
    }
  }
}
