package com.github.rougsig.meowflux.android

import java.util.*

class StateDiff<S : Any> {
  private val diffs = ArrayDeque<(ps: S?, ns: S) -> Unit>()
  private var previousState: S? = null

  fun onUpdate(
    shouldUpdate: (previousState: S, newState: S) -> Boolean,
    block: (S) -> Unit
  ) {
    diffs.add { ps, ns ->
      if (ps == null || shouldUpdate(ps, ns)) block(ns)
    }
  }

  fun update(newState: S) {
    diffs.forEach { diff -> diff(previousState, newState) }
    previousState = newState
  }
}

fun <S : Any, T> StateDiff<S>.diff(vararg mappers: (S) -> T, block: (S) -> Unit) {
  onUpdate({ p, n -> mappers.any { mapper -> mapper(p) != mapper(n) } }, block)
}

fun <S : Any> StateDiff<S>.onFirstUpdate(block: (S) -> Unit) {
  var isFirstUpdate = true
  onUpdate({ _, _ -> isFirstUpdate }, { isFirstUpdate = false; block(it) })
}
