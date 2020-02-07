package com.github.rougsig.meowflux.fakes

import com.github.rougsig.meowflux.core.Dispatcher
import com.github.rougsig.meowflux.core.Middleware

val duplicateMiddleware: Middleware<CatCounter> = { next: Dispatcher, _, _ ->
  { action ->
    next(action)
    next(action)
  }
}
