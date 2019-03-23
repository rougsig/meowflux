package com.github.rougsig.rxflux.core

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

interface Actor<S : Any, A : Action> : Middleware<S>

inline fun <S : Any, reified A : Action> createActor(
  actor: ObservableTransformer<Pair<() -> S, A>, Action>
): Actor<S, Action> {
  return object : Actor<S, Action> {
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
): Actor<S, Action> {
  return createActor<S, A>(ObservableTransformer { it.concatMap { (s, a) -> actor(s(), a) } })
}

inline fun <S : Any, reified A : Action> createFlatActor(
  crossinline actor: (state: S, action: A) -> Observable<Action>
): Actor<S, Action> {
  return createActor<S, A>(ObservableTransformer { it.flatMap { (s, a) -> actor(s(), a) } })
}

inline fun <S : Any, reified A : Action> createSwitchActor(
  crossinline actor: (state: S, action: A) -> Observable<Action>
): Actor<S, Action> {
  return createActor<S, A>(ObservableTransformer { it.switchMap { (s, a) -> actor(s(), a) } })
}

