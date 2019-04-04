package com.github.rougsig.rxflux.android.core

import toothpick.config.Module

interface ToothpickModuleBindings {
  fun bindInto(module: Module)
}

object ToothpickEmptyModuleBindings: ToothpickModuleBindings {
  override fun bindInto(module: Module) = Unit
}
