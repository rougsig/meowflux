package com.github.rougsig.rxflux2.core

abstract class FluxModule<S : Any, M : FluxMutations<S>, G : FluxGetters<S>, A : FluxActions<S, M>>(
  initialState: S
) {

  abstract val mutations: M
  abstract val actions: A
  abstract val getters: G

  @Volatile
  var state: S = initialState
    private set

  fun commit(mutate: M.(state: S) -> S) {
    synchronized(state) {
      state = mutations.mutate(state)
    }
  }

}

