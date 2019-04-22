package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.core.action.Action

fun loadTodoListAction(): Action {
  return Action.LoadTodoList
}

fun addTodoItemAction(text: String): Action {
  return Action.AddTodoItem(text)
}

fun removeTodoItemAction(id: Long): Action {
  return Action.RemoveTodoItem(id)
}
