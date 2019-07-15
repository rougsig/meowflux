package com.github.rougsig.rxflux.common

internal fun createName(
  name: String,
  parent: String?
): String {
  return if (parent != null) {
    "$parent/$name"
  } else {
    name
  }
}
