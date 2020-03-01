package com.github.rougsig.meowflux.android.demo.app

import android.app.Application
import com.github.rougsig.meowflux.android.StoreContainer
import com.github.rougsig.meowflux.android.demo.di.AppGraph
import com.github.rougsig.meowflux.android.demo.store.root.RootState
import com.github.rougsig.meowflux.core.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class MyApplication : Application(), StoreContainer<RootState>, CoroutineScope by MainScope() {
  private val appGraph: AppGraph = AppGraph(this)
  override val store: Store<RootState>
    get() = appGraph.storeGraph.store
}
