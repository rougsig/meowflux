package com.github.rougsig.rxflux.core.middleware

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.store.StateAccessor
import io.reactivex.Observable
import io.reactivex.ObservableSource

typealias ActorUpstream<S, A> = Observable<Pair<StateAccessor<S>, A>>

interface ActorTaskComposer<S : Any> {
  fun compose(upstream: ActorUpstream<S, Action>, tasks: List<ActorTask<S, Action>>): ObservableSource<Action>
}

class FlatActorTaskComposer<S : Any> : ActorTaskComposer<S> {
  override fun compose(
    upstream: ActorUpstream<S, Action>,
    tasks: List<ActorTask<S, Action>>
  ): ObservableSource<Action> {
    return upstream
      .flatMapIterable { (accessor, action) -> tasks.map { it.run(accessor.getState(), action) } }
      .concatMap { it }
  }
}

class ConcatActorTaskComposer<S : Any> : ActorTaskComposer<S> {
  override fun compose(
    upstream: ActorUpstream<S, Action>,
    tasks: List<ActorTask<S, Action>>
  ): ObservableSource<Action> {
    return upstream
      .concatMapIterable { (accessor, action) -> tasks.map { it.run(accessor.getState(), action) } }
      .concatMap { it }
  }
}

class SwitchActorTaskComposer<S : Any> : ActorTaskComposer<S> {
  override fun compose(
    upstream: ActorUpstream<S, Action>,
    tasks: List<ActorTask<S, Action>>
  ): ObservableSource<Action> {
    return upstream
      .flatMapIterable { (accessor, action) -> tasks.map { it.run(accessor.getState(), action) } }
      .switchMap { it }
  }
}
