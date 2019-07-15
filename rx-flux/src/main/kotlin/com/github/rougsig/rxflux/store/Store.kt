package com.github.rougsig.rxflux.store

import com.github.rougsig.rxflux.action.Action
import com.github.rougsig.rxflux.action.ActionFactory
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class Store<S : Any>(
  initialState: S
) : Disposable {
  private val subscribers = CompositeDisposable()
  private val actionHandlers = HashMap<String, (S, Any) -> S>()
  private val effectHandlers = HashMap<String, (Any, S) -> Observable<Action>>()

  @Volatile
  private var state = initialState
  private val actionsRelay = PublishRelay.create<Action>()
  val stateLive = actionsRelay
    .compose { upstream ->
      upstream.flatMap { action ->
        val (name, payload) = action
        effectHandlers[name]?.invoke(payload, state) ?: Observable.just(action)
      }
    }
    .scan(initialState) { state, action ->
      val (name, payload) = action
      (actionHandlers[name]?.invoke(state, payload) ?: state).also { this.state = it }
    }

  fun <T : Any> bindAction(
    factory: ActionFactory<T>,
    handler: (S, T) -> S
  ): Store<S> {
    subscribers.add(factory.actions.subscribe(actionsRelay))
    actionHandlers[factory.name] = handler as (S, Any) -> S
    return this
  }

  fun <T : Any> bindEffect(
    factory: ActionFactory<T>,
    handler: (T, S) -> Observable<Action>
  ): Store<S> {
    subscribers.add(factory.actions.subscribe(actionsRelay))
    effectHandlers[factory.name] = handler as (Any, S) -> Observable<Action>
    return this
  }

  override fun isDisposed(): Boolean {
    return subscribers.isDisposed
  }

  override fun dispose() {
    return subscribers.dispose()
  }
}
