package com.github.rougsig.rxflux.android.repository.di

import com.github.rougsig.rxflux.android.core.ToothpickModuleBindings
import com.github.rougsig.rxflux.android.repository.TodoListRepository
import com.github.rougsig.rxflux.android.repository.TodoListRepositoryImpl
import toothpick.config.Module

object TodoListRepositoryBindings : ToothpickModuleBindings {
  override fun bindInto(module: Module) {
    module
      .bind(TodoListRepository::class.java)
      .to(TodoListRepositoryImpl::class.java)
      .singletonInScope()
  }
}
