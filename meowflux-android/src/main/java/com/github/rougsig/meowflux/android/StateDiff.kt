package com.github.rougsig.meowflux.android

import java.util.*

class StateDiff<S : Any> {
  private val diffs = ArrayDeque<(ps: S?, ns: S) -> Unit>()
  private var previousState: S? = null

  fun <T> diff(
    mapper: (S) -> T,
    shouldUpdate: (previousState: T, newState: T) -> Boolean,
    block: (T) -> Unit
  ) {
    diffs.add { ps, ns ->
      val newValue = mapper(ns)
      if (ps == null || shouldUpdate(mapper(ps), newValue)) {
        block(newValue)
      }
    }
  }

  fun update(newState: S) {
    diffs.forEach { diff -> diff(previousState, newState) }
    previousState = newState
  }
}

fun <S : Any, T> StateDiff<S>.diff(mapper: (S) -> T, block: (T) -> Unit) {
  diff(mapper, { p, n -> p != n }, block)
}
