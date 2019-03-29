package com.github.rougsig.rxflux.android.domain

import com.github.rougsig.rxflux.android.domain.todolist.TodoListActorModule
import com.github.rougsig.rxflux.android.repository.TodoListModule
import toothpick.Toothpick

internal val appScope = Toothpick.openScope("APP_SCOPE").apply {
  installModules(TodoListModule())
  installModules(TodoListActorModule())
}
