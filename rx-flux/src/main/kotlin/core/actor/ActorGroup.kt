package com.github.rougsig.rxflux.core.actor

import com.github.rougsig.rxflux.core.action.Action
import io.reactivex.Observer

abstract class ActorGroup : Actor(FlatMapTaskComposer()) {
  abstract val actors: List<Actor>

  override fun accept(action: Action<*>) {
    actors.forEach { actor -> actor.accept(action) }
  }

  override fun subscribeActual(observer: Observer<in Action<*>>) {
    merge(actors).subscribe(observer)
  }
}
