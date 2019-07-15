package com.github.rougsig.rxflux.action

import com.github.rougsig.rxflux.common.createName
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

class ActionFactory<T : Any>(
  name: String,
  parent: String?
) {
  val name: String = createName(name, parent)

  private val relay = PublishRelay.create<Action>()
  internal val actions: Observable<Action> = relay

  operator fun invoke(value: T) {
    relay.accept(createAction(value))
  }

  fun createAction(value: T): Action {
    return Action(name, value)
  }
}
