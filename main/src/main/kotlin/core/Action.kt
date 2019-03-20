package com.github.rougsig.rxflux.core

interface Action {
  val name: String
    get() = this.javaClass.simpleName
}
