package com.github.rougsig.rxflux.core.middleware

import com.github.rougsig.rxflux.core.action.Action
import io.reactivex.ObservableSource

interface ActorTask<S : Any> {
  fun apply(state: S, action: Action): ObservableSource<Action>
}

@Suppress("FunctionName")
inline fun <S : Any> ActorTask(
  crossinline task: (state: S, action: Action) -> ObservableSource<Action>
): ActorTask<S> {
  return object : ActorTask<S> {
    override fun apply(state: S, action: Action): ObservableSource<Action> {
      return task(state, action)
    }
  }
}
