package com.github.rougsig.rxflux.android.ui.core.mvi

typealias Command<T> = () -> T?

inline fun <T> command(
  crossinline body: () -> Unit
): Command<T> {
  return { body(); null }
}

inline fun <T> command(
  action: T,
  crossinline body: () -> Unit
): Command<T> {
  return { body(); action }
}

fun <T> command(action: T): Command<T> {
  return { action }
}

inline fun <T> commandOn(
  value: Boolean,
  crossinline body: () -> Unit,
  noinline lazyAction: (() -> T)? = null
): Command<T>? {
  return if (value) command(lazyAction?.invoke(), body) else null
}

inline fun <T> commandOn(
  value: Boolean,
  crossinline body: () -> Unit
): Command<T>? {
  return if (value) command(null, body) else null
}

inline fun <T> commandActionOn(
  value: Boolean,
  crossinline lazyAction: () -> T
): Command<T>? {
  return if (value) command(lazyAction.invoke()) else null
}
