package com.github.rougsig.rxflux.android.domain

import com.github.rougsig.rxflux.android.core.ToothpickModuleBindings
import com.github.rougsig.rxflux.core.store.Store
import toothpick.config.Module

internal object StoreBindings : ToothpickModuleBindings {
  override fun bindInto(module: Module) {
    module
      .bind(Store::class.java)
      .toInstance(Store())
  }
}
