package com.github.rougsig.rxflux.android.domain

import com.github.rougsig.rxflux.android.domain.todolist.generated.TodoListFluxState
import com.github.rougsig.rxflux.annotations.CreateFluxState

@CreateFluxState
internal interface AppState {
  val todoList: TodoListFluxState
}
