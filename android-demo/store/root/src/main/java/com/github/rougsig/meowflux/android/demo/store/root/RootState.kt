package com.github.rougsig.meowflux.android.demo.store.root

import com.github.rougsig.meowflux.core.Action

class RootState

fun rootReducer(action: Action, previousState: RootState?): RootState {
  return RootState()
}
