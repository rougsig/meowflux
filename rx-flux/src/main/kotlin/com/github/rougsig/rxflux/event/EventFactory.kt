package com.github.rougsig.rxflux.event

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Observer

class EventFactory<T : Any>(
  val name: String
): Observable<T>() {
  private val relay = PublishRelay.create<T>()

  operator fun invoke(value: T) {
    relay.accept(value)
  }

  override fun subscribeActual(observer: Observer<in T>) {
    relay.subscribe(observer)
  }
}
