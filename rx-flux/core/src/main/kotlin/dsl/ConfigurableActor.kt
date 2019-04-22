package com.github.rougsig.rxflux.dsl

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.middleware.*
import io.reactivex.ObservableSource
import kotlin.reflect.KClass

abstract class ConfigurableActor(
  composer: ActorTaskComposer = ConcatMapActorTaskComposer()
) : Actor(composer) {
  private val _tasks = mutableListOf<ActorTask<Any>>()
  override val tasks: List<ActorTask<Any>> = _tasks

  fun <A : Any> task(
    type: KClass<A>,
    task: (A) -> ObservableSource<Action<*>>
  ): ConfigurableActor = apply {
    _tasks.add(ActorTaskImpl(type, task))
  }
}
