package com.github.rougsig.rxflux.android.core

import com.github.rougsig.rxflux.core.action.Action
import io.reactivex.Observable

fun <T> Observable<T>.toLceEventObservable(
  stateCreator: (LceState<T>) -> Action
): Observable<Action> {
  return this
    .map { stateCreator(LceState.Content(it)) }
    .onErrorReturn { stateCreator(LceState.Error(it.localizedMessage)) }
    .startWith(stateCreator(LceState.Loading()))
}

fun <T> Observable<T>.skipFirstIf(value: Boolean): Observable<T> {
  return if (value) this.skip(1) else this
}

fun Observable<Action>.actionOnSuccess(block: () -> Action): Observable<Action> {
  return this.mergeWith(Observable.fromCallable { block() })
}
