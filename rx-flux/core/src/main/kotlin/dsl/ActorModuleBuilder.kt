package com.github.rougsig.rxflux.dsl

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.dispatcher.Dispatcher
import com.github.rougsig.rxflux.core.middleware.*
import com.github.rougsig.rxflux.core.store.StateAccessor

open class ActorGroupBuilder<S : Any> {
  private val actors = mutableListOf<Actor<S>>()

  fun <A : Action> actor(
    composer: ActorTaskComposer,
    setup: ActorBuilder<S, A>.() -> Unit = {}
  ): ActorGroupBuilder<S> {
    val builder = ActorBuilder<S, A>(composer)
    builder.setup()
    actors.add(builder.build())
    return this
  }

  fun <A : Action> flatMapActor(
    setup: ActorBuilder<S, A>.() -> Unit = {}
  ): ActorGroupBuilder<S> {
    return actor(FlatMapTaskComposer(), setup)
  }

  fun <A : Action> concatMapActor(
    setup: ActorBuilder<S, A>.() -> Unit = {}
  ): ActorGroupBuilder<S> {
    return actor(ConcatMapActorTaskComposer(), setup)
  }

  fun <A : Action> switchMapActor(
    setup: ActorBuilder<S, A>.() -> Unit = {}
  ): ActorGroupBuilder<S> {
    return actor(SwitchMapActorTaskComposer(), setup)
  }

  internal fun build(): ActorGroupImpl<S> {
    return ActorGroupImpl(actors)
  }
}

abstract class ConfigurableActor<S : Any, A : Action> : Middleware<S>, ActorGroupBuilder<S>() {
  private val actor = this.build()

  final override fun apply(accessor: StateAccessor<S>, dispatcher: Dispatcher): (Dispatcher) -> Dispatcher {
    return actor.apply(accessor, dispatcher)
  }
}
