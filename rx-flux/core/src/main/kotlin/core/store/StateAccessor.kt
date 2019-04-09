package com.github.rougsig.rxflux.core.store

interface StateAccessor<S : Any> {
  fun getState(): S
}

@Suppress("FunctionName")
inline fun <S : Any> StateAccessor(crossinline accessor: () -> S): StateAccessor<S> {
  return object : StateAccessor<S> {
    override fun getState(): S {
      return accessor()
    }
  }
}
