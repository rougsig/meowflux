package com.github.rougsig.rxflux.android.domain.app

import com.github.rougsig.rxflux.android.domain.todolist.generated.TodoListFluxState
import com.github.rougsig.rxflux.annotations.CreateFluxState

@CreateFluxState
private interface AppState {
  val todoList: TodoListFluxState
}
