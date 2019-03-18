package com.example.test

import com.github.rougsig.rxflux.annotations.FluxState

@FluxState
private interface LoginState {
  val phone: String?
  val password: String?
}
