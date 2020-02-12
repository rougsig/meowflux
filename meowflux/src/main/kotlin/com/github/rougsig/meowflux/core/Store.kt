package com.github.rougsig.meowflux.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface Store<S : Any> : StoreDispatcher {
  fun getState(): S
  val stateFlow: Flow<S>
}

@FlowPreview
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
fun <S : Any> CoroutineScope.createStore(
  reducer: Reducer<S>,
  initialState: S? = null,
  middleware: List<Middleware<S>> = emptyList()
): Store<S> = BaseStore(this, reducer, initialState, middleware)
