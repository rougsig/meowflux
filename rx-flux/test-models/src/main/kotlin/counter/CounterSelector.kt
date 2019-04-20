package com.example.test.counter

import com.github.rougsig.rxflux.core.selector.Selector
import com.github.rougsig.rxflux.core.store.StateAccessor

class CounterSelector(
  accessor: StateAccessor<CounterState>
) : Selector<CounterState>(accessor) {
  val count = createSelector { count }

  val countLive = createSelectorLive(count)
}
