package com.github.rougsig.rxflux.android.domain.app

import com.github.rougsig.rxflux.android.domain.app.generated.AppFluxState
import com.github.rougsig.rxflux.android.domain.todolist.TodoListActor
import com.github.rougsig.rxflux.android.domain.todolist.TodoListReducer
import com.github.rougsig.rxflux.core.middleware.WrappedMiddleware
import com.github.rougsig.rxflux.core.store.FluxStore
import javax.inject.Inject

internal class AppStore @Inject constructor(
  todoListReducer: TodoListReducer,
  todoListActor: TodoListActor
) : FluxStore<AppFluxState>(
  AppFluxState.combineReducers(
    todoListReducer = todoListReducer
  ),
  WrappedMiddleware(todoListActor) { it.todoList }
)
