package com.github.rougsig.rxflux.core.middleware

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.store.StateAccessor
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

typealias ActorUpstream<S, A> = Observable<Pair<StateAccessor<S>, A>>
typealias ActorTaskComposer = ObservableTransformer<ObservableSource<Action>, Action>

class FlatMapTaskComposer : ActorTaskComposer {
  override fun apply(upstream: Observable<ObservableSource<Action>>): ObservableSource<Action> {
    return upstream.flatMap { it }
  }
}

class ConcatMapActorTaskComposer : ActorTaskComposer {
  override fun apply(upstream: Observable<ObservableSource<Action>>): ObservableSource<Action> {
    return upstream.concatMap { it }
  }
}

class SwitchMapActorTaskComposer : ActorTaskComposer {
  override fun apply(upstream: Observable<ObservableSource<Action>>): ObservableSource<Action> {
    return upstream.switchMap { it }
  }
}
