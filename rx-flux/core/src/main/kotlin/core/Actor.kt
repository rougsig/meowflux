package com.github.rougsig.rxflux.core

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

inline fun <S : Any, reified A : Action> createActor(
  actor: ObservableTransformer<Pair<() -> S, A>, Action>
): Middleware<S> {
  return object : Middleware<S> {
    override fun invoke(getState: () -> S, dispatch: Dispatcher): (Dispatcher) -> Dispatcher {
      val relay = PublishRelay.create<Action>()

      relay
        .ofType(A::class.java)
        .map { getState to it }
        .compose(actor)
        .subscribe(dispatch)

      return { next ->
        { action ->
          relay.accept(action)
          next(action)
        }
      }
    }
  }
}

inline fun <S : Any, reified A : Action> createConcatActor(
  crossinline actor: (state: S, action: A) -> Observable<Action>
): Middleware<S> {
  return createActor<S, A>(ObservableTransformer { it.concatMap { (s, a) -> actor(s(), a) } })
}

inline fun <S : Any, reified A : Action> createFlatActor(
  crossinline actor: (state: S, action: A) -> Observable<Action>
): Middleware<S> {
  return createActor<S, A>(ObservableTransformer { it.flatMap { (s, a) -> actor(s(), a) } })
}

inline fun <S : Any, reified A : Action> createSwitchActor(
  crossinline actor: (state: S, action: A) -> Observable<Action>
): Middleware<S> {
  return createActor<S, A>(ObservableTransformer { it.switchMap { (s, a) -> actor(s(), a) } })
}

