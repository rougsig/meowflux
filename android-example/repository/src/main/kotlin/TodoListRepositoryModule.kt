package com.github.rougsig.rxflux.android.repository

import toothpick.config.Module

class TodoListRepositoryModule : Module() {
  init {
    this
      .bind(TodoListRepository::class.java)
      .to(TodoListRepositoryImpl::class.java)
      .singletonInScope()
  }
}
