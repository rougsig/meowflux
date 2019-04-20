package com.github.rougsig.rxflux.core.selector

import com.github.rougsig.rxflux.core.store.StateAccessor
import io.reactivex.ObservableSource

abstract class Selector<S : Any>(
  private val accessor: StateAccessor<S>
) {
  protected fun <T> createSelector(selector: S.() -> T): (S) -> T {
    return { selector(accessor.getState()) }
  }

  protected fun <T> createSelectorLive(selector: S.() -> T): ObservableSource<T> {
    return accessor.getStateLive().map(selector)
  }
}
