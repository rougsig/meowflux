package com.github.rougsig.meowflux.fakes

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.extension.createTypedReducer
import com.github.rougsig.meowflux.fakes.CatCounterAction.*

data class CatCounter(
  val catCount: Int = 0
)

sealed class CatCounterAction : Action {
  object Increment : CatCounterAction()
  object Decrement : CatCounterAction()
  data class SetValue(val newValue: Int) : CatCounterAction()
}

val catCounterReducer = createTypedReducer(CatCounter()) { action: CatCounterAction, previousState ->
  when (action) {
    is Decrement -> previousState.copy(catCount = previousState.catCount - 1)
    is Increment -> previousState.copy(catCount = previousState.catCount + 1)
    is SetValue -> previousState.copy(catCount = action.newValue)
  }
}
