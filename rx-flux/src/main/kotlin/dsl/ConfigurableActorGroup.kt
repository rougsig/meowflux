package com.github.rougsig.rxflux.dsl

import com.github.rougsig.rxflux.core.actor.*

abstract class ConfigurableActorGroup : ActorGroup() {
  private val _actors = mutableListOf<Actor>()
  override val actors: List<Actor> = _actors

  fun actor(
    composer: ActorTaskComposer,
    setup: ActorBuilder.() -> Unit = {}
  ): ConfigurableActorGroup {
    val actor = ActorBuilder(namespace, composer)
    actor.setup()
    _actors.add(actor)
    return this
  }

  fun flatMapActor(
    setup: ActorBuilder.() -> Unit = {}
  ): ConfigurableActorGroup {
    return actor(FlatMapTaskComposer(), setup)
  }

  fun concatMapActor(
    setup: ActorBuilder.() -> Unit = {}
  ): ConfigurableActorGroup {
    return actor(ConcatMapActorTaskComposer(), setup)
  }

  fun switchMapActor(
    setup: ActorBuilder.() -> Unit = {}
  ): ConfigurableActorGroup {
    return actor(SwitchMapActorTaskComposer(), setup)
  }

  class ActorBuilder(
    override val prefix: String,
    composer: ActorTaskComposer
  ) : ConfigurableActor(composer) {
    override val namespace: String
      get() = prefix
  }
}
