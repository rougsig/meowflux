package com.github.rougsig.rxflux.core.middleware

import com.github.rougsig.rxflux.core.action.Action
import io.reactivex.ObservableSource
import kotlin.reflect.KClass

interface ActorTask<S : Any, A : Action> {
  val type: KClass<A>

  fun run(state: S, action: A): ObservableSource<Action>
}

internal class ActorTaskImpl<S : Any, A : Action>(
  override val type: KClass<A>,
  private val task: (S, A) -> ObservableSource<Action>
) : ActorTask<S, A> {
  override fun run(state: S, action: A): ObservableSource<Action> {
    return task(state, action)
  }
}
