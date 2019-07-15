package com.github.rougsig.rxflux.event

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

class EventFactory<T : Any>(
  val name: String
) {
  private val relay = PublishRelay.create<T>()
  internal val events: Observable<T> = relay

  operator fun invoke(value: T) {
    relay.accept(value)
  }
}
