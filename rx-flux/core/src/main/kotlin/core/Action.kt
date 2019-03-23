package com.github.rougsig.rxflux.core

abstract class Action {
  val name: String
    get() = this.javaClass.simpleName
}

internal object InitAction: Action()
