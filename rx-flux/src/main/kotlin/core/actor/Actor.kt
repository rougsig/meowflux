package com.github.rougsig.rxflux.core.actor

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.action.ActionCreator
import io.reactivex.Observable
import io.reactivex.functions.Consumer

interface Actor :
  Consumer<Action>,
  ActionCreator {
  val actionLive: Observable<Action>
}
