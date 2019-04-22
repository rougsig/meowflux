package com.github.rougsig.rxflux.android.domain.todolist.actor

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.actor.Actor

interface TodoListActor: Actor {
  fun loadTodoList(): Action

  fun addTodoItem(text: String): Action

  fun removeTodoItem(id: Long): Action
}
