package com.github.rougsig.rxflux.android.ui.todolist.di

import com.github.rougsig.rxflux.android.domain.todolist.di.TodoListBindings
import toothpick.config.Module

internal class ConnectModule : Module() {
  init {
    TodoListBindings
      .bindInto(this)

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
