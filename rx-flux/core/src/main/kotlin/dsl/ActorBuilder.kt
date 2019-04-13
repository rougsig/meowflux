package com.github.rougsig.rxflux.dsl

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.middleware.*
import io.reactivex.ObservableSource
import kotlin.reflect.KClass

class ActorBuilder<S : Any, A : Action>(
  private val composer: ActorTaskComposer
) {
  private val tasks = mutableListOf<ActorTask<S, A>>()

  fun <T : A> task(
    type: KClass<T>,
    task: (S, T) -> ObservableSource<Action>
  ): ActorBuilder<S, A> {
    tasks.add(ActorTaskImpl(type, task) as ActorTask<S, A>)
    return this
  }

  internal fun build(): Actor<S> {
    return ActorImpl(
      tasks,
      composer
    )
  }
}
