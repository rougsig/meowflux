package com.github.rougsig.rxflux2.core

import kotlin.reflect.KProperty

abstract class FluxGetters<S : Any> {
  abstract val previousState: () -> S?
  abstract val state: () -> S

  inner class Getter<T>(
    private val getter: S.() -> T
  ) {
    @Synchronized
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
      var memorized: T? = null
      if (memorized == null || previousState() !== state()) {
        memorized = getter(state())
      }
      return memorized!!
    }
  }
}
