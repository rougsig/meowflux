package com.github.rougsig.rxflux.core.action

data class Action<T>(
  val namespace: String,
  val payload: T
)
