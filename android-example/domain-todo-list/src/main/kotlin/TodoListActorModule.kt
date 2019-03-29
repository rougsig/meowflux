package com.github.rougsig.rxflux.android.domain.todolist

import toothpick.config.Module

class TodoListActorModule : Module() {
  init {
    this
      .bind(TodoListActor::class.java)
      .singletonInScope()
  }
}
