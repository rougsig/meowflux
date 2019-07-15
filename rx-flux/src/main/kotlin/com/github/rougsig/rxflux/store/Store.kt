package com.github.rougsig.rxflux.store

import com.github.rougsig.rxflux.event.EventFactory
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class Store<S : Any>(
  initialState: S
) : Disposable {
  private val subscribes = CompositeDisposable()
  private val handlers = HashMap<String, (S, Any) -> S>()

  private val eventsRelay = PublishRelay.create<Pair<String, Any>>()
  val stateLive = eventsRelay.scan(initialState) { state, (name, event) ->
    handlers[name]!!.invoke(state, event)
  }

  fun <T : Any> bind(
    factory: EventFactory<T>,
    handler: (S, T) -> S
  ): Store<S> {
    subscribes.add(factory.events.map { factory.name to it }.subscribe(eventsRelay))
    handlers[factory.name] = handler as (S, Any) -> S
    return this
  }

  override fun isDisposed(): Boolean {
    return subscribes.isDisposed
  }

  override fun dispose() {
    return subscribes.dispose()
  }
}
