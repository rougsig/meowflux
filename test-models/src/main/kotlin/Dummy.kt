package com.example.test

import com.example.test.generated.AnimalFluxState
import com.example.test.generated.CatFluxState
import com.example.test.generated.DuckFluxState
import com.github.rougsig.rxflux.core.Action
import com.github.rougsig.rxflux.core.combineReducers
import com.github.rougsig.rxflux.core.createReducer
import com.github.rougsig.rxflux.core.reduce

private val catReducer = createReducer<CatFluxState, Action>({ CatFluxState(0) }) { s, _ -> s }
private val duckReducer = createReducer<DuckFluxState, Action>({ DuckFluxState(0) }) { s, _ -> s }

object MyAction : Action

fun main() {
  val reducer = combineReducers(
    AnimalFluxState.FIELDS.catState reduce catReducer,
    AnimalFluxState.FIELDS.duckState reduce duckReducer
  )

  val state = reducer.invoke(null, MyAction)
  println(state)
}

