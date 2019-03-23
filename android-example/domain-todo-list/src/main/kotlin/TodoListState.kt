package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.annotations.CreateFluxState

@CreateFluxState
interface TodoListState {
  val todoListItems: LceState<List<TodoItem>>?
  val addTodoItem: LceState<Unit>?
  val removeTodoItem: LceState<Unit>?
}
