package com.github.rougsig.rxflux.event

import java.util.*

inline fun <reified T : Any> createEvent(
  name: String = UUID.randomUUID().toString()
): EventFactory<T> {
  return EventFactory(name)
}
