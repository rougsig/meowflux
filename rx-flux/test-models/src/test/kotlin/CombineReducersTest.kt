package com.example.test

import com.example.test.CombineReducersTest.AnimalAction.CatCounterAction
import com.example.test.CombineReducersTest.AnimalAction.DuckCounterAction
import com.example.test.generated.AnimalFluxState
import com.example.test.generated.CatFluxState
import com.example.test.generated.DuckFluxState
import com.github.rougsig.rxflux.core.Action
import com.github.rougsig.rxflux.core.safeReducer
import junit.framework.TestCase

class CombineReducersTest : TestCase() {

  sealed class AnimalAction : Action() {
    sealed class CatCounterAction : AnimalAction() {
      object Inc : CatCounterAction()
      object Dec : CatCounterAction()
    }

    sealed class DuckCounterAction : AnimalAction() {
      object Inc : DuckCounterAction()
      object Dec : DuckCounterAction()
    }
  }

  fun testGeneratedCombineReducers() {
    val catReducer = safeReducer(CatFluxState(0)) { s: CatFluxState, a: CatCounterAction ->
      when (a) {
        CatCounterAction.Inc ->
          s.setCounter(s.counter + 1)
        CatCounterAction.Dec ->
          s.setCounter(s.counter - 1)
      }
    }

    val duckReducer = safeReducer(DuckFluxState(0)) { s: DuckFluxState, a: DuckCounterAction ->
      when (a) {
        DuckCounterAction.Inc ->
          s.setCounter(s.counter + 1)
        DuckCounterAction.Dec ->
          s.setCounter(s.counter - 1)
      }
    }

    val animalReducer = AnimalFluxState.combineReducers(
      catStateReducer = catReducer,
      duckStateReducer = duckReducer
    )

    val catInt = animalReducer.reduce(null, CatCounterAction.Inc)
    assertEquals(1, catInt.catState.counter)
    assertEquals(0, catInt.duckState.counter)

    val duckInt = animalReducer.reduce(null, DuckCounterAction.Inc)
    assertEquals(1, duckInt.duckState.counter)
    assertEquals(0, duckInt.catState.counter)
  }
}
