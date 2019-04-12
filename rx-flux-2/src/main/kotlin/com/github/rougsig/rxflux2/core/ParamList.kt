package com.github.rougsig.rxflux2.core

class ParamList internal constructor(vararg val values: Any?) {

  private fun <T> elementAt(i: Int): T {
    return if (values.size > i) {
      values[i] as T
    } else {
      throw IllegalArgumentException("Can't get parameter value #$i from $this")
    }
  }

  operator fun <T> component1(): T = elementAt(0)
  operator fun <T> component2(): T = elementAt(1)
  operator fun <T> component3(): T = elementAt(2)
  operator fun <T> component4(): T = elementAt(3)
  operator fun <T> component5(): T = elementAt(4)
  operator fun <T> component6(): T = elementAt(5)
  operator fun <T> component7(): T = elementAt(6)
  operator fun <T> component8(): T = elementAt(7)
  operator fun <T> component9(): T = elementAt(8)

  inline fun <reified T> get() = values.first { it is T }
}
