package com.github.rougsig.rxflux.core.reducer

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.action.ActionCreator
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Consumer

abstract class Reducer<S : Any>(
  initialState: S
) :
  Observable<S>(),
  Consumer<Action<*>>,
  ActionCreator {

  protected open val mutators: List<Mutator<S, Any>> = emptyList()

  private val stateRelay = BehaviorRelay.createDefault(initialState)
  private val actionQueue = PublishRelay.create<Action<*>>()

  val state: S
    get() = stateRelay.value!!

  init {
    @Suppress
    actionQueue
      .filter { it.namespace == namespace }
      .map { it.payload }
      .scan(initialState) { state, action ->
        mutators.fold(state) { acc, mutator ->
          mutator.mutate(acc, action)
        }
      }
      .subscribe(stateRelay)
  }

  override fun accept(action: Action<*>) {
    actionQueue.accept(action)
  }

  override fun subscribeActual(observer: Observer<in S>) {
    stateRelay.subscribe(observer)
  }
}
