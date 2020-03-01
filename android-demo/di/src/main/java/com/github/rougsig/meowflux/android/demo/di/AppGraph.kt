package com.github.rougsig.meowflux.android.demo.di

import kotlinx.coroutines.CoroutineScope

interface AppGraph {
  val storeGraph: StoreGraph

  companion object {
    operator fun invoke(applicationScope: CoroutineScope): AppGraph {
      return AppGraphImpl(
        storeGraph = StoreGraph(applicationScope)
      )
    }
  }
}

private class AppGraphImpl(
  override val storeGraph: StoreGraph
) : AppGraph
