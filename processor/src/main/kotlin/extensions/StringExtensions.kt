package com.github.rougsig.rxflux.processor.extensions

internal fun String.beginWithUpperCase(): String {
  return when (length) {
    0 -> ""
    1 -> toUpperCase()
    else -> first().toUpperCase() + substring(1)
  }
}

fun String.beginWithLowerCase(): String {
  return when (length) {
    0 -> ""
    1 -> toLowerCase()
    else -> first().toLowerCase() + substring(1)
  }
}

