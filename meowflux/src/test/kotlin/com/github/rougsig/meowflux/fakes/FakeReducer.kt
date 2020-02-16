package com.github.rougsig.meowflux.fakes

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Reducer
import java.util.*

class FakeReducer : Reducer<Unit> {
  val actionHistory = LinkedList<Action>()

  override fun invoke(action: Action, previousState: Unit?) {
    actionHistory.add(action)
  }
}
