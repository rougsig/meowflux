package com.github.rougsig.rxflux.core.actor

import com.github.rougsig.rxflux.core.action.Action
import io.reactivex.Observable
import io.reactivex.Observer

abstract class ActorGroup : Actor {
  abstract val actors: List<TaskedActor>

  override fun accept(action: Action) {
    actors.forEach { actor -> actor.accept(action) }
  }

  override fun subscribe(observer: Observer<in Action>) {
    Observable.merge(actors).subscribe(observer)
  }
}
