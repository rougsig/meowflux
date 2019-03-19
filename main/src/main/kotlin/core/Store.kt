//package com.github.rougsig.rxflux.core
//
//interface Store<S : Any, A : Action> {
//  fun dispatch(action: A)
//  val state: S
//}
//
//fun <S : Any, A : Action> createStore(reducer: Reducer<S, A>, initialState: S): Store<S, A> {
//  return object : Store<S, A> {
//    override var state = initialState
//      private set
//
//    override fun dispatch(action: A) {
//      state = reducer.invoke(state, action)
//    }
//  }
//}
