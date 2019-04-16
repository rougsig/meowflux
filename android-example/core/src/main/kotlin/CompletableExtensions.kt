package com.github.rougsig.rxflux.android.core

import com.github.rougsig.rxflux.core.action.Action
import io.reactivex.Completable
import io.reactivex.Observable

fun Completable.toLceEventObservable(
  stateCreator: (LceState<Unit>) -> Action
): Observable<Action> {
  return this
    .andThen(Observable.fromCallable { stateCreator(LceState.Content(Unit)) })
    .onErrorReturn { stateCreator(LceState.Error(it.localizedMessage)) }
    .startWith(stateCreator(LceState.Loading()))
}
