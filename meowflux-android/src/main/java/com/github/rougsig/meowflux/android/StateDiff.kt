package com.github.rougsig.meowflux.android

import java.util.*

class StateDiff<S : Any> {
  private val diffs = ArrayDeque<(ps: S?, ns: S) -> Unit>()
  private var previousState: S? = null

  fun <T> diff(
    mapper: (S) -> T,
    comparator: (newValue: T, oldValue: T) -> Boolean,
    block: (T) -> Unit
  ) {
    diffs.add { ps, ns ->
      val newValue = mapper(ns)
      if (ps == null || comparator(newValue, mapper(ps))) {
        block(newValue)
      }
    }
  }

  fun update(newState: S) {
    diffs.forEach { it(previousState, newState) }
    previousState = newState
  }
}

fun <S : Any, T> StateDiff<S>.diff(mapper: (S) -> T, block: (T) -> Unit) {
  diff(mapper, { newValue, oldValue -> newValue == oldValue }, block)
}
