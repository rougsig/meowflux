package com.github.rougsig.rxflux.dsl

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.dispatcher.Dispatcher
import com.github.rougsig.rxflux.core.middleware.*
import com.github.rougsig.rxflux.core.store.StateAccessor

open class ActorGroupBuilder<S : Any> {
  private val actors = mutableListOf<Actor<S>>()

  fun <A : Action> flatMapActor(
    setup: ActorBuilder<S, A>.() -> Unit = {}
  ): ActorGroupBuilder<S> {
    val builder = ActorBuilder<S, A>(FlatActorTaskComposer())
    builder.setup()
    actors.add(builder.build())
    return this
  }

  fun <A : Action> concatMapActor(
    setup: ActorBuilder<S, A>.() -> Unit = {}
  ): ActorGroupBuilder<S> {
    val builder = ActorBuilder<S, A>(ConcatActorTaskComposer())
    builder.setup()
    actors.add(builder.build())
    return this
  }

  fun <A : Action> switchMapActor(
    setup: ActorBuilder<S, A>.() -> Unit = {}
  ): ActorGroupBuilder<S> {
    val builder = ActorBuilder<S, A>(SwitchActorTaskComposer())
    builder.setup()
    actors.add(builder.build())
    return this
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
