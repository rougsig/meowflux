package com.github.rougsig.rxflux.store

fun <S : Any> createStore(initialState: S): Store<S> {
  return Store(initialState)
}
