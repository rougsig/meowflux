package com.github.rougsig.meowflux.core

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

interface Store<S : Any> {
  val state: S
  val stateFlow: Flow<S>
  fun dispatch(action: Action): Job
}

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
    stateChannel.offer(reducer(action, stateChannel.valueOrNull))
  }) { prevDispatcher, nextMiddleware ->
    nextMiddleware(this, ::dispatchRoot, stateChannel::value, prevDispatcher)
  }

  private fun dispatchRoot(action: Action) {
    dispatcher(action)
  }

  init {
    initialState?.let(stateChannel::sendBlocking)
    dispatch(StoreInit)
  }

  override val stateFlow = stateChannel.asFlow()
  override val state: S
    get() = stateChannel.value

  override fun dispatch(action: Action) = launch {
    dispatchRoot(action)
  }
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
