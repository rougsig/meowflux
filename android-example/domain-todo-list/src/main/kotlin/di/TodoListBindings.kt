package com.github.rougsig.rxflux.android.domain.todolist.di

import com.github.rougsig.rxflux.android.core.ToothpickModuleBindings
import com.github.rougsig.rxflux.android.domain.todolist.TodoListActor
import com.github.rougsig.rxflux.android.domain.todolist.TodoListReducer
import com.github.rougsig.rxflux.android.repository.di.TodoListRepositoryBindings
import toothpick.config.Module

object TodoListBindings : ToothpickModuleBindings {
  override fun bindInto(module: Module) {
    TodoListRepositoryBindings.bindInto(module)

    module
      .bind(TodoListActor::class.java)
      .singletonInScope()

    module
      .bind(TodoListReducer::class.java)
      .singletonInScope()
  }
}
