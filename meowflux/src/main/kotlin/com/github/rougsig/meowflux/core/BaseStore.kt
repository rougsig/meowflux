package com.github.rougsig.meowflux.core

open class BaseStore<S : Any>(
  private val reducer: Reducer<S>,
  initialState: S? = null,
  middleware: List<Middleware<S>> = emptyList()
) : Store<S> {
  private var stateListener: ((S) -> Unit)? = null
  private var state: S? = initialState
    set(value) {
      field = value
      stateListener?.invoke(value!!)
    }

  private val dispatcher = middleware.reversed().fold<Middleware<S>, Dispatcher>({ action ->
    state = reducer(action, state)
  }) { next, nextMiddleware ->
    nextMiddleware(::dispatchRoot, ::getState)(next)
  }

  private fun dispatchRoot(action: Action) {
    println("thread -> ${Thread.currentThread().id}")
    dispatcher(action)
  }

  init {
    dispatchRoot(MeowFluxInit)
  }

  final override fun subscribe(listener: (S) -> Unit) {
    this.stateListener = listener
  }

  final override fun unsubscribe() {
    this.stateListener = null
  }

  final override fun getState(): S {
    return state!!
  }

  final override fun dispatch(action: Action) {
    dispatchRoot(action)
  }
}
