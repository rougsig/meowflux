package com.github.rougsig.rxflux.core.middleware

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.store.StateAccessor
import io.reactivex.Observable
import io.reactivex.ObservableSource

typealias ActorUpstream<S, A> = Observable<Pair<StateAccessor<S>, A>>

fun <S : Any, A : Action> ActorUpstream<S, A>.concatTasks(
  vararg tasks: ActorTask<S>
): ObservableSource<Action> {
  return this
    .concatMapIterable { (accessor, action) -> tasks.map { it.apply(accessor.getState(), action) } }
    .concatMap { it }
}

fun <S : Any, A : Action> ActorUpstream<S, A>.flatTasks(
  vararg tasks: ActorTask<S>
): ObservableSource<Action> {
  return this
    .concatMapIterable { (accessor, action) -> tasks.map { it.apply(accessor.getState(), action) } }
    .flatMap { it }
}

fun <S : Any, A : Action> ActorUpstream<S, A>.switchTasks(
  vararg tasks: ActorTask<S>
): ObservableSource<Action> {
  return this
    .concatMapIterable { (accessor, action) -> tasks.map { it.apply(accessor.getState(), action) } }
    .switchMap { it }
}
