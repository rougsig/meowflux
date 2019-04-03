package com.github.rougsig.rxflux.android.domain.app

import com.github.rougsig.rxflux.android.domain.app.generated.AppFluxState
import com.github.rougsig.rxflux.android.domain.todolist.TodoListActor
import com.github.rougsig.rxflux.android.domain.todolist.TodoListReducer
import com.github.rougsig.rxflux.core.Store
import com.github.rougsig.rxflux.core.createStore
import com.github.rougsig.rxflux.core.wrapMiddleware
import javax.inject.Inject

internal class AppStore @Inject constructor(
  private val todoListReducer: TodoListReducer,
  private val todoListActor: TodoListActor
) : Store<AppFluxState> by {
  createStore(
    AppFluxState.combineReducers(
      todoListReducer = todoListReducer
    ),
    wrapMiddleware(todoListActor) { it.todoList }
  )
}()
