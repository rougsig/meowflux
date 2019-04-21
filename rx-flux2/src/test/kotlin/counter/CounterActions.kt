package com.github.rougsig.rxflux2.counter

import com.github.rougsig.rxflux2.core.FluxActions
import io.reactivex.Single

class CounterActions(
  mutations: CounterMutations,
  getters: CounterGetters
) : FluxActions<CounterState, CounterMutations, CounterGetters, CounterActions>(
  mutations,
  getters
) {

  val inc = createAction { context ->
    Single.fromCallable {
      context
        .commit { it.inc() }
    }
  }

}
