package com.github.rougsig.rxflux.android.domain.todolist

import toothpick.config.Module

class TodoListFluxModule : Module() {
  init {
    this
      .bind(TodoListActor::class.java)
      .singletonInScope()

    this
      .bind(TodoListReducer::class.java)
      .singletonInScope()

  }
}
