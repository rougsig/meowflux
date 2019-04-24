package com.github.rougsig.rxflux.core.reducer

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.action.ActionCreator
import io.reactivex.Observable
import io.reactivex.functions.Consumer

interface Reducer<S : Any> :
  ActionCreator,
  Consumer<Action> {
  val state: S
  val stateLive: Observable<S>

  fun <T : Any> selectLive(fieldSelector: S.() -> T?): Observable<T>
  fun <T : Any> select(fieldSelector: S.() -> T?): T?
}
