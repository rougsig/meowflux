package com.github.rougsig.rxflux.core.middleware

import com.github.rougsig.rxflux.core.store.StateAccessor

@Suppress("FunctionName")
inline fun <I : Any, O : Any> WrappedMiddleware(
  middleware: Middleware<O>,
  crossinline mapper: (I) -> O
): Middleware<I> {
  return Middleware { accessor, next ->
    middleware.apply(StateAccessor { mapper(accessor.getState()) }, next)
  }
}
