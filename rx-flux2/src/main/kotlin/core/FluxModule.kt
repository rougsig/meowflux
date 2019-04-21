package com.github.rougsig.rxflux2.core

import io.reactivex.Single

abstract class FluxModule<S : Any, M : FluxMutations<S>, G : FluxGetters<S>, A : FluxActions<S, M, G, A>>(
  initialState: S
) {

  abstract val mutations: M
  abstract val getters: G
  abstract val actions: A

  @Volatile
  var previousState: S? = null
    private set

  @Volatile
  var state: S = initialState
    private set

  fun commit(mutate: M.(state: S) -> S) {
    synchronized(state) {
      state = mutations.mutate(state)
      if (previousState !== state) {
        previousState = state
      }
    }
  }

  fun dispatch(action: A.(FluxContext<S, M, G, A>) -> Single<FluxContext<S, M, G, A>>) {
    actions
      .action(FluxContext(mutations, getters, actions))

  }
}

