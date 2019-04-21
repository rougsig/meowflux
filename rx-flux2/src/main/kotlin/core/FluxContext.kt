package com.github.rougsig.rxflux2.core

import io.reactivex.Single
import java.util.*

class FluxContext<S : Any, M : FluxMutations<S>, G : FluxGetters<S>, A : FluxActions<S, M, G, A>>(
  private val mutations: M,
  private val getters: G,
  private val actions: A
) {
  private val mutationQueue = LinkedList<(S) -> S>()

  fun commit(mutate: M.(state: S) -> S) = apply {
    mutationQueue.add { state -> mutations.mutate(state) }
  }

  fun dispatch(action: A.() -> Single<FluxContext<S, M, G, A>>): Single<FluxContext<S, M, G, A>> {
    return actions.action()
  }
}
