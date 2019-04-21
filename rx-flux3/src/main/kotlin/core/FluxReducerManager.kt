package com.github.rougsig.rxflux3.core

import io.reactivex.Observable
import java.util.*

class FluxReducerManager(
  actionsObservable: Observable<FluxAction>
) {
  private val reducers = IdentityHashMap<String, FluxReducer<*>>()

  init {
    @Suppress
    actionsObservable
      .subscribe { action ->
        reducers
          .values
          .forEach { it.accept(action) }
      }
  }

  fun addReducer(reducer: FluxReducer<*>): Boolean {
    val isExists = reducers.containsKey(reducer.namespace)

    if (!isExists) {
      reducers[reducer.namespace] = reducer
    }

    return !isExists
  }

  fun removeReducer(reducer: FluxReducer<*>): Boolean {
    return reducers.remove(reducer.namespace, reducer)
  }
}
