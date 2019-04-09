package com.github.rougsig.rxflux.core.action

abstract class Action {
  val name: String
    get() = this.javaClass.simpleName
}
