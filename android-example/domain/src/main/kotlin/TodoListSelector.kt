package com.github.rougsig.rxflux.android.domain

import com.github.rougsig.rxflux.android.core.distinctFieldChanges
import com.github.rougsig.rxflux.android.domain.todolist.TodoListActorAction

val loadTodoList = { store.dispatch(TodoListActorAction.LoadTodoList) }
val addTodoItem = { text: String -> store.dispatch(TodoListActorAction.AddTodoItem(text)) }
val removeTodoItem = { id: Long -> store.dispatch(TodoListActorAction.RemoveTodoItem(id)) }

val loadTodoListChanges = store.stateLive.distinctFieldChanges { it.todoList.todoListItems }
val addTodoItemChanges = store.stateLive.distinctFieldChanges { it.todoList.addTodoItem }
val removeTodoItemChanges = store.stateLive.distinctFieldChanges { it.todoList.removeTodoItem }
