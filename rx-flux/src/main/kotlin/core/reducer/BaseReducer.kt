package com.github.rougsig.rxflux.core.reducer

import com.github.rougsig.rxflux.core.action.Action
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

abstract class BaseReducer<S : Any>(
  initialState: S
) : Reducer<S> {

  protected open val mutators: List<Mutator<S, Any>> = emptyList()

  private val stateRelay = BehaviorRelay.createDefault(initialState)
  private val actionQueue = PublishRelay.create<Action>()

  override val state: S
    get() = stateRelay.value!!

  override val stateLive: Observable<S>
    get() = stateRelay

  init {
    @Suppress
    actionQueue
      .filter { it.namespace == namespace }
      .map { it.payload }
      .scan(initialState) { state, action ->
        mutators.fold(state) { acc, mutator ->
          mutator.mutate(acc, action)
        }
      }
      .subscribe(stateRelay)
  }

  override fun accept(action: Action) {
    actionQueue.accept(action)
  }

  override fun <T : Any> select(fieldSelector: (S) -> T?): Observable<T> {
    return stateLive
      .filter { fieldSelector(it) != null }
      .map { fieldSelector(it)!! }
      .distinctUntilChanged()
  }

  fun <T> createSelector(fieldSelector: S.() -> T?): (S) -> T? {
    return fieldSelector
  }
}
