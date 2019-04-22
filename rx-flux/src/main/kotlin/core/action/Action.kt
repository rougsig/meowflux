package com.github.rougsig.rxflux.core.action

data class Action<T> internal constructor(
  val namespace: String,
  val payload: T
)
