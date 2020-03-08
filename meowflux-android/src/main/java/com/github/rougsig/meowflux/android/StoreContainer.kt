package com.github.rougsig.meowflux.android

import android.content.Context
import com.github.rougsig.meowflux.core.Store

interface StoreContainer<S : Any> {
  val store: Store<S>
}

fun <S : Any> Context.store(): Store<S> {
  val storeContainer = this.applicationContext as? StoreContainer<S>
  requireNotNull(storeContainer) {
    "failed to get store from the context because application does not implement StoreContainer."
  }
  return storeContainer.store
}
