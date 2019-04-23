package com.github.rougsig.rxflux.core.actor

import com.github.rougsig.rxflux.core.action.Action
import io.reactivex.Observable

abstract class ActorGroup : Actor {
  abstract val actors: List<TaskedActor>

  override fun accept(action: Action) {
    actors.forEach { actor -> actor.accept(action) }
  }

  override val actionLive: Observable<Action> by lazy { Observable.merge(actors.map(Actor::actionLive)) }
}
