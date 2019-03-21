package com.example.test

import com.example.test.generated.AnimalFluxState
import com.example.test.generated.CatFluxState
import com.example.test.generated.DuckFluxState
import com.github.rougsig.rxflux.core.Action
import com.github.rougsig.rxflux.core.createReducer
import junit.framework.TestCase

class CombineReducersTest : TestCase() {

  sealed class CatCounterAction : Action {
    object Inc : CatCounterAction()
    object Dec : CatCounterAction()
  }

  sealed class DuckCounterAction : Action {
    object Inc : DuckCounterAction()
    object Dec : DuckCounterAction()
  }

  private val catReducer = createReducer(CatFluxState(0)) { s: CatFluxState, a: CatCounterAction ->
    when (a) {
      CatCounterAction.Inc ->
        s.setCounter(s.counter + 1)
      CatCounterAction.Dec ->
        s.setCounter(s.counter - 1)
    }
  }

  private val duckReducer = createReducer(DuckFluxState(0)) { s: DuckFluxState, a: DuckCounterAction ->
    when (a) {
      DuckCounterAction.Inc ->
        s.setCounter(s.counter + 1)
      DuckCounterAction.Dec ->
        s.setCounter(s.counter - 1)
    }
  }

  fun testCombineReducers() {
    val animalReducer = AnimalFluxState.combineReducers(
      catStateReducer = catReducer,
      duckStateReducer = duckReducer
    )

    val catInt = animalReducer.invoke(null, CatCounterAction.Inc)
    assertEquals(1, catInt.catState.counter)
    assertEquals(0, catInt.duckState.counter)

    val duckInt = animalReducer.invoke(null, DuckCounterAction.Inc)
    assertEquals(1, duckInt.duckState.counter)
    assertEquals(0, duckInt.catState.counter)
  }
}
