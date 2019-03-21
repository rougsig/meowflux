package com.example.test

import com.github.rougsig.rxflux.annotations.CreateFluxState

class Phone<T, T2, T3>

@CreateFluxState
private interface LoginState {
  val phone: Phone<Int, Long, Unit>
  val password: String?
}
