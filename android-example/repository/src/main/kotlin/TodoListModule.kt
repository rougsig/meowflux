package com.github.rougsig.rxflux.android.repository

import toothpick.config.Module

class TodoListModule : Module() {
  init {
    this
      .bind(TodoListRepository::class.java)
      .to(TodoListRepositoryImpl::class.java)
      .singletonInScope()
  }
}
