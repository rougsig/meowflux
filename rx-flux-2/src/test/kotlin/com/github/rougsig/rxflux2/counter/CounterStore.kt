package com.github.rougsig.rxflux2.counter

import com.github.rougsig.rxflux2.core.Action
import com.github.rougsig.rxflux2.core.Mutation
import com.github.rougsig.rxflux2.core.RxFlux2
import com.github.rougsig.rxflux2.counter.CounterStore.*
import io.reactivex.Observable

class CounterStore : RxFlux2.Store<State, Mutations, Getters, Actions>(State()) {
  object Mutations {
    val increment: Mutation<State> = { (state: State) ->
      state.copy(count = state.count + 1)
    }

    val incrementBy: Mutation<State> = { (state: State, incBy: Int) ->
      state.copy(count = state.count + incBy)
    }
  }

  object Actions {
    val increment: Action<State, Mutations, Getters, Actions> = { context ->
      Observable.just(context.commit(Mutations.increment))
    }
  }

  object Getters {
    val count = { state: State -> state.count }
  }

  data class State(
    val count: Int = 0
  )

  override val getters: Getters
    get() = Getters

  override val mutations: Mutations
    get() = Mutations

  override val actions: Actions
    get() = Actions
}
