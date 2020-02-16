package com.github.rougsig.meowflux.fakes

import com.github.rougsig.meowflux.fakes.CatCounterAction.FetchCatCounter
import com.github.rougsig.meowflux.fakes.CatCounterAction.SetValue
import com.github.rougsig.meowflux.worker.Watcher
import com.github.rougsig.meowflux.worker.watcher
import com.github.rougsig.meowflux.worker.Worker
import com.github.rougsig.meowflux.worker.worker
import kotlinx.coroutines.flow.mapNotNull

class CatCounterFetcher : Worker<FetchCatCounter, CatCounter> by worker({ action ->
  println("+100500 (worker) $action")
  put(SetValue(newValue = 100))
})

class CatCounterWatcher(
  private val worker: CatCounterFetcher
) : Watcher<FetchCatCounter, CatCounter> by watcher(worker, {
  mapNotNull { action ->
    println("+100500 (watcher) $action")
    action as? FetchCatCounter
  }
})
