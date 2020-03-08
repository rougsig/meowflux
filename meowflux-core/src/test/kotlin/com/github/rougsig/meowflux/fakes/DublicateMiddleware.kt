package com.github.rougsig.meowflux.fakes

import com.github.rougsig.meowflux.core.Middleware

val duplicateMiddleware: Middleware<CatCounter> = { _, _, _, next ->
  { action ->
    next(action)
    next(action)
  }
}
