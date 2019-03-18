package com.github.rougsig.rxflux.processor.extensions

internal fun String.beginWithUpperCase(): String {
  return when (length) {
    0 -> ""
    1 -> toUpperCase()
    else -> first().toUpperCase() + substring(1)
  }
}
