package com.github.rougsig.rxflux.core.actor

import com.github.rougsig.rxflux.core.action.Action
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

abstract class TaskedActor(
  composer: ActorTaskComposer
) : Actor {

  protected open val tasks: List<ActorTask<Any>> = emptyList()

  private val actionRelay = PublishRelay.create<Action>()
  private val actionQueue = PublishRelay.create<Action>()

  init {
    @Suppress
    actionQueue
      .filter { it.namespace == namespace }
      .map { it.payload }
      .flatMapIterable { action -> tasks.map { it.run(action) } }
      .compose(composer)
      .subscribe(actionRelay)
  }

  override val actionLive: Observable<Action> = actionRelay

  override fun accept(action: Action) {
    actionQueue.accept(action)
  }
}
