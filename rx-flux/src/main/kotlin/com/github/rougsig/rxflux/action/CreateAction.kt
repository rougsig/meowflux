package com.github.rougsig.rxflux.action

import java.util.*

fun <T : Any> createAction(
  name: String = UUID.randomUUID().toString(),
  parent: String? = null
): ActionFactory<T> {
  return ActionFactory(name, parent)
}
