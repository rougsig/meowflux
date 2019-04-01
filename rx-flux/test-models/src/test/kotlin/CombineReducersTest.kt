package com.example.test

import com.example.test.CombineReducersTest.AnimalAction.CatCounterAction
import com.example.test.CombineReducersTest.AnimalAction.DuckCounterAction
import com.example.test.generated.AnimalFluxState
import com.example.test.generated.CatFluxState
import com.example.test.generated.DuckFluxState
import com.github.rougsig.rxflux.core.Action
import com.github.rougsig.rxflux.core.combineReducers
import com.github.rougsig.rxflux.core.createReducer
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
    val catReducer = createReducer(CatFluxState(0)) { s: CatFluxState, a: CatCounterAction ->
      when (a) {
        CatCounterAction.Inc ->
          s.setCounter(s.counter + 1)
        CatCounterAction.Dec ->
          s.setCounter(s.counter - 1)
      }
    }

    val duckReducer = createReducer(DuckFluxState(0)) { s: DuckFluxState, a: DuckCounterAction ->
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

    val catInt = animalReducer.invoke(null, CatCounterAction.Inc)
    assertEquals(1, catInt.catState.counter)
    assertEquals(0, catInt.duckState.counter)

    val duckInt = animalReducer.invoke(null, DuckCounterAction.Inc)
    assertEquals(1, duckInt.duckState.counter)
    assertEquals(0, duckInt.catState.counter)
  }

  fun testCombineReducers() {
    val catReducer = { ps: AnimalFluxState, a: AnimalAction ->
      val s = ps.catState
      val ns = when (a) {
        CatCounterAction.Inc ->
          s.setCounter(s.counter + 1)
        CatCounterAction.Dec ->
          s.setCounter(s.counter - 1)
        else ->
          s
      }
      ps.setCatState(ns)
    }

    val duckReducer = { ps: AnimalFluxState, a: AnimalAction ->
      val s = ps.duckState
      val ns = when (a) {
        DuckCounterAction.Inc ->
          s.setCounter(s.counter + 1)
        DuckCounterAction.Dec ->
          s.setCounter(s.counter - 1)
        else ->
          s
      }
      ps.setDuckState(ns)
    }

    val animalReducer = combineReducers<AnimalFluxState, AnimalAction>(
      AnimalFluxState(DuckFluxState(0), CatFluxState(0)),
      catReducer,
      duckReducer
    )

    val catInt = animalReducer.invoke(null, CatCounterAction.Inc)
    assertEquals(1, catInt.catState.counter)
    assertEquals(0, catInt.duckState.counter)

    val duckInt = animalReducer.invoke(null, DuckCounterAction.Inc)
    assertEquals(1, duckInt.duckState.counter)
    assertEquals(0, duckInt.catState.counter)
  }
}
