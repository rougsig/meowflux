package com.github.rougsig.rxflux.android.repository

import com.github.rougsig.rxflux.android.core.ToothpickModuleBindings
import toothpick.config.Module

object TodoListBindings : ToothpickModuleBindings {
  override fun bindInto(module: Module) {
    module
      .bind(TodoListRepository::class.java)
      .to(TodoListRepositoryImpl::class.java)
      .singletonInScope()
  }
}
