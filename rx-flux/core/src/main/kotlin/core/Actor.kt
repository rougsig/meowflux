package com.github.rougsig.rxflux.core

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

interface Actor<S : Any, A : Action> : Middleware<S>

inline fun <S : Any, reified A : Action> createActor(
  actor: ObservableTransformer<Pair<() -> S, A>, Action>
): Actor<S, A> {
  return object : Actor<S, A> {
    override fun create(state: () -> S, nextDispatcher: Dispatcher): Dispatcher {
      val relay = PublishRelay.create<Action>()
      relay
        .ofType(A::class.java)
        .map { state to it }
        .compose(actor)
        .subscribe(nextDispatcher::dispatch)

      return createDispatcher { action ->
        nextDispatcher.dispatch(action)
        relay.accept(action)
      }
    }
  }
}

inline fun <S : Any, reified A : Action> createConcatActor(
  crossinline actor: (state: S, action: A) -> Observable<Action>
): Actor<S, A> {
  return createActor(ObservableTransformer { it.concatMap { (s, a) -> actor(s(), a) } })
}

inline fun <S : Any, reified A : Action> createFlatActor(
  crossinline actor: (state: S, action: A) -> Observable<Action>
): Actor<S, A> {
  return createActor(ObservableTransformer { it.flatMap { (s, a) -> actor(s(), a) } })
}

inline fun <S : Any, reified A : Action> createSwitchActor(
  crossinline actor: (state: S, action: A) -> Observable<Action>
): Actor<S, A> {
  return createActor(ObservableTransformer { it.switchMap { (s, a) -> actor(s(), a) } })
}

