package com.github.rougsig.rxflux.android.domain.appstate

import com.github.rougsig.rxflux.android.domain.todolist.TodoListState
import com.github.rougsig.rxflux.annotations.CreateFluxState

@CreateFluxState
internal interface AppState {
  val todoList: TodoListState
}
