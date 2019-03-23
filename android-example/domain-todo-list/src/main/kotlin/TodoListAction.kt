package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.core.Action

sealed class TodoListActorAction : Action() {
  object LoadTodoList : TodoListActorAction()
  data class AddTodoItem(val text: String) : TodoListActorAction()
  data class RemoveTodoItem(val id: Long) : TodoListActorAction()
}
