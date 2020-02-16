package com.github.rougsig.meowflux.core

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class BaseStore<S : Any>(
  private val scope: CoroutineScope,
  private val reducer: Reducer<S>,
  initialState: S? = null,
  middleware: List<Middleware<S>> = emptyList()
) : Store<S>, CoroutineScope by scope + newSingleThreadContext("Store") {
  private val stateChannel = ConflatedBroadcastChannel<S>()

  private val dispatcher = middleware.reversed().fold<Middleware<S>, Dispatcher>({ action ->
    stateChannel.send(reducer(action, stateChannel.valueOrNull))
  }) { prevDispatcher, nextMiddleware ->
    nextMiddleware(this, ::dispatchRoot, stateChannel::value, prevDispatcher)
  }

  private suspend fun dispatchRoot(action: Action) {
    dispatcher(action)
  }

  init {
    initialState?.let(stateChannel::sendBlocking)
    dispatch(MeowFluxInit)
  }

  override fun getState() = stateChannel.value
  override val stateFlow = stateChannel.asFlow()
  override fun dispatch(action: Action) = launch { dispatcher(action) }
}

@FlowPreview
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@Suppress("FunctionName")
fun <S : Any> CoroutineScope.Store(
  reducer: Reducer<S>,
  initialState: S? = null,
  middleware: List<Middleware<S>> = emptyList()
): Store<S> = BaseStore(this, reducer, initialState, middleware)
