package com.github.rougsig.rxflux2.core

abstract class FluxModule<S : Any, M : FluxMutations<S>, G : FluxGetters<S>, A : FluxActions<S, M>>(
  initialState: S,
  val mutations: M,
  val getters: G,
  val actions: A
) :
  FluxMutations<S> by mutations,
  FluxGetters<S> by getters,
  FluxActions<S, M> by actions {

  @Volatile
  var state: S = initialState
    private set

  fun commit(mutate: M.(state: S) -> S) {
    synchronized(state) {
      state = mutations.mutate(state)
    }
  }

}

