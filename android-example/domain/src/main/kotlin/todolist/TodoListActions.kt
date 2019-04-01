package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.core.Action

fun loadTodoListAction(): Action {
  return TodoListActorAction.LoadTodoList
}

fun addTodoItemAction(text: String): Action {
  return TodoListActorAction.AddTodoItem(text)
}

fun removeTodoItemAction(id: Long): Action {
  return TodoListActorAction.RemoveTodoItem(id)
}
