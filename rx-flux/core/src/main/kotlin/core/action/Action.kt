package com.github.rougsig.rxflux.core.action

abstract class Action {
  open val name: String
    get() = this.javaClass.simpleName
}

data class ScopedAction(
  val action: Action,
  val scope: String
) : Action()
