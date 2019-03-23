package com.github.rougsig.rxflux.android.core

import com.github.rougsig.rxflux.core.Action
import io.reactivex.Completable
import io.reactivex.Observable

fun Completable.toLceEventObservable(
  actionOnSuccess: (() -> Action)? = null,
  stateCreator: (LceState<Unit>) -> Action
): Observable<Action> {
  return this
    .andThen(Observable.fromCallable { stateCreator(LceState.Content(Unit)) })
    .let { if (actionOnSuccess != null) it.mergeWith(Observable.fromCallable { actionOnSuccess() }) else it }
    .onErrorReturn { stateCreator(LceState.Error(it.localizedMessage)) }
    .startWith(stateCreator(LceState.Loading()))
}
