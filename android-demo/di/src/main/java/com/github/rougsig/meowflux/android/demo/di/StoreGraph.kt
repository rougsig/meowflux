package com.github.rougsig.meowflux.android.demo.di

import com.github.rougsig.meowflux.android.demo.store.root.Store
import com.github.rougsig.meowflux.android.demo.store.root.rootReducer
import kotlinx.coroutines.CoroutineScope
import com.github.rougsig.meowflux.core.Store as createStore

interface StoreGraph {
  val store: Store

  companion object {
    operator fun invoke(applicationScope: CoroutineScope): StoreGraph {
      return StoreGraphImpl(applicationScope)
    }
  }
}

private class StoreGraphImpl(applicationScope: CoroutineScope) : StoreGraph {
  override val store: Store = applicationScope.createStore(
    reducer = ::rootReducer,
    initialState = null,
    middleware = emptyList()
  )
}
