package com.github.rougsig.rxflux.android.core

import toothpick.Scope

inline fun <reified T> Scope.instance(tag: String? = null): T = this.getInstance(T::class.java, tag)
