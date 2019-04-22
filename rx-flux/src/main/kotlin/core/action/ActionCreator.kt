package com.github.rougsig.rxflux.core.action

interface ActionCreator {
  val prefix: String
    get() = ""

  val namespace: String
    get() = if (prefix.isBlank()) {
      this.javaClass.simpleName
    } else {
      "$prefix-${this.javaClass.simpleName}"
    }

  fun createAction(block: () -> Any): Action {
    return Action(namespace, block.invoke())
  }
}
