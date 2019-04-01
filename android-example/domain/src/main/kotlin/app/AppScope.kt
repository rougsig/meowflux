package com.github.rougsig.rxflux.android.domain.app

import com.github.rougsig.rxflux.android.domain.todolist.TodoListFluxModule
import com.github.rougsig.rxflux.android.repository.TodoListRepositoryModule
import toothpick.Toothpick

internal val appScope = Toothpick.openScope("APP_SCOPE").apply {
  installModules(TodoListRepositoryModule())
  installModules(TodoListFluxModule())
}
