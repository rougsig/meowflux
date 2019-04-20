package com.github.rougsig.rxflux.core.store

import io.reactivex.Observable

interface StateAccessor<S : Any> {
  fun getState(): S
  fun getStateLive(): Observable<S>
}

@Suppress("FunctionName")
inline fun <S : Any> StateAccessor(crossinline accessor: () -> S): StateAccessor<S> {
  return object : StateAccessor<S> {
    override fun getState(): S {
      return accessor()
    }
  }
}
