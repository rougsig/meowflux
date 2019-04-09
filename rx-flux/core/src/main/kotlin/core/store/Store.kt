package com.github.rougsig.rxflux.core.store

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.dispatcher.Dispatcher
import com.github.rougsig.rxflux.core.middleware.Middleware
import com.github.rougsig.rxflux.core.reducer.Reducer
import io.reactivex.Observable

interface Store<S : Any> : Dispatcher {
  val state: S
  val stateLive: Observable<S>
}

@Suppress("FunctionName")
fun <S : Any> Store(
  reducer: Reducer<S, Action>,
  vararg middleware: Middleware<S>,
  initialState: S? = null
): Store<S> {
  return object : BaseStore<S>(reducer, *middleware, initialState = initialState) {}
}
