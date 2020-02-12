package com.github.rougsig.meowflux.core.fakes

import com.github.rougsig.meowflux.core.Middleware
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.coroutineContext

val duplicateMiddleware: Middleware<CatCounter> = { _, _, next ->
  { action ->
    next(action)
    next(action)
  }
}
