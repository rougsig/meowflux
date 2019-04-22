package com.github.rougsig.rxflux.core.reducer

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.action.ActionCreator
import io.reactivex.Observable
import io.reactivex.functions.Consumer

interface Reducer<S : Any> :
  ActionCreator,
  Consumer<Action> {
  fun <T : Any> select(fieldSelector: S.() -> T?): Observable<T>
}
