package com.github.rougsig.rxflux.event

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observer

class EventFactory<T : Any>(
  val name: String
) {
  private val relay = PublishRelay.create<T>()

  operator fun invoke(value: T) {
    relay.accept(value)
  }

  fun subscribe(observer: Observer<T>) {
    relay.subscribe(observer)
  }
}
