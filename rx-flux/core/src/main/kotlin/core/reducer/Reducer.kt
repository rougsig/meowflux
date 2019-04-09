package com.github.rougsig.rxflux.core.reducer

import com.github.rougsig.rxflux.core.action.Action

interface Reducer<S, A> {
  fun reduce(state: S?, action: A): S
}

@Suppress("FunctionName")
inline fun <S> Reducer(crossinline reducer: (S?, Action) -> S): Reducer<S, Action> {
  return object : Reducer<S, Action> {
    override fun reduce(state: S?, action: Action): S {
      return reducer(state, action)
    }
  }
}
