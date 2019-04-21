package com.github.rougsig.rxflux2.core

import io.reactivex.Single

typealias FluxAction<S, M, G, A> = (context: FluxContext<S, M, G, A>) -> Single<FluxContext<S, M, G, A>>

abstract class FluxActions<S : Any, M : FluxMutations<S>, G : FluxGetters<S>, A : FluxActions<S, M, G, A>>(
  protected val mutations: M,
  protected val getters: G
) {

  fun createAction(
    action: FluxAction<S, M, G, A>
  ): FluxAction<S, M, G, A> {
    return action
  }

}
