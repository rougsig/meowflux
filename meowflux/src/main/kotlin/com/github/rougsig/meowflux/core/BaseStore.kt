package com.github.rougsig.meowflux.core

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class BaseStore<S : Any> constructor(
  private val storeScope: CoroutineScope,
  private val reducer: Reducer<S>,
  initialState: S? = null,
  middlewares: List<Middleware<S>> = emptyList(),
  storeName: String = "Store"
) : Store<S> {
  private val context = newSingleThreadContext(storeName)
  private var isDispatching = false
  private val stateChannel = ConflatedBroadcastChannel<S>()

  private val dispatcher: Dispatcher

  override val stateFlow: Flow<S> = stateChannel.asFlow()

  init {
    initialState?.let(stateChannel::sendBlocking)
    dispatcher = middlewares.fold<Middleware<S>, Dispatcher>(::dispatchInternal) { dispatcher, middleware ->
      middleware(::dispatchInternal, ::getState, dispatcher)
    }
    dispatch(MeowFluxInit)
  }

  override fun getState(): S {
    return stateChannel.value
  }

  override fun dispatch(action: Action): Job {
    return storeScope.launch(context) { dispatcher(action) }
  }

  private suspend fun dispatchInternal(action: Action) {
    if (isDispatching) error("ConcurrentModificationException: MeowFlux now isDispatching")

    isDispatching = true
    stateChannel.send(reducer(action, stateChannel.valueOrNull))
    isDispatching = false
  }
}
