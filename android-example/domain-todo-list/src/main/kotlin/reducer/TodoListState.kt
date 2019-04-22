package com.github.rougsig.rxflux.android.domain.todolist.reducer

import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.enitity.TodoItem

data class TodoListState(
  val todoListItems: LceState<List<TodoItem>>? = null,
  val addTodoItem: LceState<Unit>? = null,
  val removeTodoItem: LceState<Unit>? = null
)
