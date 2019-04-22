package com.github.rougsig.rxflux.core.action

data class Action internal constructor(
  val namespace: String,
  val payload: Any
)
