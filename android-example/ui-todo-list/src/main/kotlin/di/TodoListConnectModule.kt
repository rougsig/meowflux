package com.github.rougsig.rxflux.android.ui.todolist.di

import toothpick.config.Module

internal class TodoListConnectModule : Module() {
  init {
    this
      .bind(StateProps::class.java)
      .to(StatePropsImpl::class.java)
      .singletonInScope()

    this
      .bind(DispatchProps::class.java)
      .to(DispatchPropsImpl::class.java)
      .singletonInScope()
  }
}
