package com.example.test

import com.github.rougsig.rxflux.annotations.CreateFluxState

@CreateFluxState
private interface LoginState {
  val phone: String
  val password: String?
}
