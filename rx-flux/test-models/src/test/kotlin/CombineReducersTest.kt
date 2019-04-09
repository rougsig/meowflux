package com.example.test

import com.example.test.CombineReducersTest.AnimalAction.CatCounterAction
import com.example.test.CombineReducersTest.AnimalAction.DuckCounterAction
import com.example.test.generated.AnimalFluxState
import com.example.test.generated.CatFluxState
import com.example.test.generated.DuckFluxState
import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.dsl.ConfigurableReducer
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

  class CatReducer : ConfigurableReducer<CatFluxState, CatCounterAction>(
    CatCounterAction::class,
    CatFluxState(0)
  ) {
    init {
      mutator(CatCounterAction.Inc::class) { s, _ -> s.setCounter(s.counter + 1) }
      mutator(CatCounterAction.Dec::class) { s, _ -> s.setCounter(s.counter - 1) }
    }
  }

  class DuckReducer : ConfigurableReducer<DuckFluxState, DuckCounterAction>(
    DuckCounterAction::class,
    DuckFluxState(0)
  ) {
    init {
      mutator(DuckCounterAction.Inc::class) { s, _ -> s.setCounter(s.counter + 1) }
      mutator(DuckCounterAction.Dec::class) { s, _ -> s.setCounter(s.counter - 1) }
    }
  }


  fun testGeneratedCombineReducers() {
    val catReducer = CatReducer()
    val duckReducer = DuckReducer()

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
