package com.github.rougsig.rxflux.dsl

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.dispatcher.Dispatcher
import com.github.rougsig.rxflux.core.middleware.*
import com.github.rougsig.rxflux.core.store.StateAccessor
import io.reactivex.ObservableSource
import kotlin.reflect.KClass

class ActorBuilder<S : Any, A : Action>(
  private val composer: ActorTaskComposer<S>
) {
  private val tasks = mutableListOf<ActorTask<S, Action>>()

  fun <T : A> task(
    type: KClass<T>,
    task: (S, T) -> ObservableSource<Action>
  ): ActorBuilder<S, A> {
    tasks.add(ActorTaskImpl(type, task))
    return this
  }

  internal fun build(): Actor<S> {
    return ActorImpl(
      tasks,
      composer
    )
  }
}
