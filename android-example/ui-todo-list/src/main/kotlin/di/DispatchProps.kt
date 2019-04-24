package com.github.rougsig.rxflux.android.ui.todolist.di

import com.github.rougsig.rxflux.android.domain.todolist.actor.TodoListActor
import com.github.rougsig.rxflux.android.ui.core.routing.RouterActor
import com.github.rougsig.rxflux.core.store.Store
import javax.inject.Inject

internal interface DispatchProps {
  val loadTodoList: () -> Unit
  val showCreateTodoItemScreen: () -> Unit
  val showTodoItemDetailsScreen: (id: Long) -> Unit
}

internal class DispatchPropsImpl @Inject constructor(
  private val store: Store,
  private val todoListActor: TodoListActor,
  private val routerActor: RouterActor
) : DispatchProps {
  override val loadTodoList = { store.dispatch(todoListActor.loadTodoList()) }
  override val showCreateTodoItemScreen = { store.dispatch(routerActor.showCreateTodoItem()) }
  override val showTodoItemDetailsScreen = { id: Long -> store.dispatch(routerActor.showTodoItemDetails(id)) }
}
