package com.github.rougsig.rxflux3.core

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Consumer

abstract class FluxReducer<S : Any>(
  initialState: S
) :
  Observable<S>(),
  Consumer<FluxAction> {

  open val namespace: String = ""

  protected val state = BehaviorRelay.createDefault(initialState)
  private val actionObservable = PublishRelay.create<FluxAction>()
  private val mutators = mutableListOf<FluxMutator<S, FluxAction>>()

  init {
    @Suppress
    actionObservable
      .scan(initialState) { s, a -> s }
      .subscribe(state)
  }

  override fun accept(action: FluxAction) {
    actionObservable.accept(action)
  }

  override fun subscribeActual(observer: Observer<in S>) {
    state.subscribe(observer)
  }

  abstract fun setupReducer()
}
