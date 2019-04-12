package com.github.rougsig.rxflux2.core

object RxFlux2 {
  abstract class Store<S, M, G, A>(
    initialState: S
  ) {
    var state: S = initialState
      private set

    abstract val getters: G

    abstract val mutations: M

    abstract val actions: A

    fun commit(
      mutation: Mutation<S>,
      vararg params: Any?
    ) {
      state = mutation.invoke(ParamList(state, *params))
    }

    fun <T> get(getter: (S) -> T) = getter(state)

    fun dispatch(action: Action<S, M, G, A>) {
      action.invoke(
        Context(
          { state },
          getters,
          mutations,
          actions
        )
      )
    }
  }

  class Context<S, M, G, A>(
    val getState: () -> S,
    val getters: G,
    val mutations: M,
    val actions: A
  ) {
    private val commits = mutableListOf<() -> S>()

    fun commit(
      mutation: Mutation<S>,
      vararg params: Any?
    ) {
      commits.add { mutation.invoke(ParamList(getState(), *params)) }
    }
  }
}
