package com.example.test

import com.github.rougsig.rxflux.annotations.CreateFluxState

@CreateFluxState
private interface DuckState {
  val counter: Int
}

@CreateFluxState
private interface CatState {
  val counter: Int
}

@CreateFluxState
private interface AnimalState {
  val duckState: DuckState
  val catState: CatState
}
