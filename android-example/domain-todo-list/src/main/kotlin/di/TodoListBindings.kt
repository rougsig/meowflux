package com.github.rougsig.rxflux.android.domain.todolist.di

import com.github.rougsig.rxflux.android.core.ToothpickModuleBindings
import com.github.rougsig.rxflux.android.domain.todolist.actor.TodoListActor
import com.github.rougsig.rxflux.android.domain.todolist.actor.TodoListActorImpl
import com.github.rougsig.rxflux.android.domain.todolist.reducer.TodoListReducer
import com.github.rougsig.rxflux.android.domain.todolist.reducer.TodoListReducerImpl
import com.github.rougsig.rxflux.android.repository.di.TodoListRepositoryBindings
import toothpick.config.Module

object TodoListBindings : ToothpickModuleBindings {
  override fun bindInto(module: Module) {
    TodoListRepositoryBindings.bindInto(module)

    module
      .bind(TodoListReducer::class.java)
      .to(TodoListReducerImpl::class.java)
      .singletonInScope()

    module
      .bind(TodoListActor::class.java)
      .to(TodoListActorImpl::class.java)
      .singletonInScope()
  }
}
