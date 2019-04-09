package com.github.rougsig.rxflux.android.domain.di

import com.github.rougsig.rxflux.android.core.ToothpickModuleBindings
import com.github.rougsig.rxflux.android.domain.app.AppStore
import com.github.rougsig.rxflux.core.store.BaseStore
import com.github.rougsig.rxflux.core.store.Store
import toothpick.config.Module

internal object StoreBindings : ToothpickModuleBindings {
  override fun bindInto(module: Module) {
    module
      .bind(Store::class.java)
      .to(AppStore::class.java)
      .singletonInScope()
  }
}
