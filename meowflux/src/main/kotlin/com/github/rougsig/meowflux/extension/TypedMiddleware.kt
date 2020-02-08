package com.github.rougsig.meowflux.extension

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Dispatcher
import com.github.rougsig.meowflux.core.Middleware

inline fun <reified A : Action, S : Any> createTypedMiddleware(
  crossinline middleware: suspend (action: A, root: Dispatcher, state: () -> S, next: Dispatcher) -> Unit
): Middleware<S> = { root, state, next ->
  { action ->
    if (action is A) middleware(action, root, state, next)
    else next(action)
  }
}
