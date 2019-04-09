package com.github.rougsig.rxflux.core.middleware

import com.github.rougsig.rxflux.core.action.Action
import io.reactivex.Observable
import io.reactivex.ObservableSource
import kotlin.reflect.KClass

interface ActorTask<S : Any, A : Action> {
  fun run(state: S, action: A): ObservableSource<Action>
}

internal class ActorTaskImpl<S : Any, A : Action>(
  private val type: KClass<A>,
  private val task: (state: S, action: A) -> ObservableSource<Action>
) : ActorTask<S, Action> {
  override fun run(state: S, action: Action): ObservableSource<Action> {
    return if (type.java.isInstance(action)) {
      task(state, action as A)
    } else {
      Observable.empty()
    }
  }
}
