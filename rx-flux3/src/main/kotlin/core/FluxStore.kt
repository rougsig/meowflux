package com.github.rougsig.rxflux3.core

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.functions.Consumer

class FluxStore : Consumer<FluxAction> {
  private val actionObservable = PublishRelay.create<FluxAction>()
  private val reducerManager = FluxReducerManager(actionObservable)

  override fun accept(action: FluxAction) {
    actionObservable.accept(action)
  }

  fun addReducer(reducer: FluxReducer<*>): Boolean {
    return reducerManager.addReducer(reducer)
  }

  fun removeReducer(reducer: FluxReducer<*>): Boolean {
    return reducerManager.removeReducer(reducer)
  }
}
