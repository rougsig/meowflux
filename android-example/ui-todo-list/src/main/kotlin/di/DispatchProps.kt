package com.github.rougsig.rxflux.android.ui.todolist.di

import com.github.rougsig.rxflux.android.domain.todolist.actor.TodoListActor
import com.github.rougsig.rxflux.core.store.Store
import javax.inject.Inject

internal interface DispatchProps {
  val loadTodoList: () -> Unit
  val showCreateTodoItemScreen: () -> Unit
  val showTodoItemDetailsScreen: (id: Long) -> Unit
}

internal class DispatchPropsImpl @Inject constructor(
  private val store: Store,
  private val todoListActor: TodoListActor
) : DispatchProps {
  override val loadTodoList = { store.dispatch(todoListActor.loadTodoList()) }
  override val showCreateTodoItemScreen = {}
  override val showTodoItemDetailsScreen = { id: Long -> }
}
