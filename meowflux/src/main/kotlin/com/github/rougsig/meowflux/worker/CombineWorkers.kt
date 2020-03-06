package com.github.rougsig.meowflux.worker

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun <S : Any> combineWorkers(vararg workers: Worker<S>): Worker<S> {
  return worker@{ actions ->
    coroutineScope {
      workers.forEach { worker ->
        launch { worker(this@worker, actions) }
      }
    }
  }
}
