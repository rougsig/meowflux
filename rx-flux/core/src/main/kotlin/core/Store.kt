package com.github.rougsig.rxflux.core

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

interface Store<S : Any> {
  val state: S
  val stateLive: Observable<S>

  fun dispatch(action: Action)
}

fun <S : Any> createStore(
  reducer: Reducer<S, Action>,
  vararg middleware: Middleware<S>,
  initialState: S? = null
): Store<S> {
  return object : Store<S> {
    @Volatile
    private var store: S? = initialState
      set(value) {
        field = value
        value?.let { relay.accept(it) }
      }

    private val dispatcher: Dispatcher
    private val relay = PublishRelay.create<S>()

    override val state: S
      get() = store!!

    override val stateLive: Observable<S>
      get() = relay

    init {
      dispatchAction(InitAction)

      dispatcher = middleware
        .reversed()
        .fold({ a: Action -> dispatchAction(a) }, { dispatcher, middleware ->
          middleware({ state }, { a: Action -> dispatch(a) })(dispatcher)
        })
    }

    override fun dispatch(action: Action) {
      dispatcher(action)
    }

    private fun dispatchAction(action: Action) {
      synchronized(this) {
        val newStore = reducer.invoke(store, action)
        if (newStore !== store) {
          store = newStore
        }
      }
    }
  }
}

fun <SP: Any, DP: Any, R: Any, S: Any> connectToStore(
  store: Store<S>,
  mapStateToProps: (state: Observable<S>) -> SP,
  mapDispatchToProps: (dispatcher: Dispatcher) -> DP,
  constructor: (SP, DP) -> R
): () -> R {
  return { constructor(mapStateToProps(store.stateLive), mapDispatchToProps(store::dispatch)) }
}
