package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.core.Action

fun loadTodoListAction(): Action = TodoListActorAction.LoadTodoList

fun addTodoItemAction(text: String): Action = TodoListActorAction.AddTodoItem(text)

fun removeTodoItemAction(id: Long): Action = TodoListActorAction.RemoveTodoItem(id)
