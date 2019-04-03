package com.github.rougsig.rxflux.android.domain.di

import com.github.rougsig.rxflux.android.domain.todolist.di.TodoListBindings
import toothpick.config.Module

class StoreModule : Module() {
  init {
    TodoListBindings.bindInto(this)

    StoreBindings.bindInto(this)
  }
}
