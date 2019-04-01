package com.github.rougsig.rxflux.android.domain.app

import com.github.rougsig.rxflux.android.domain.app.generated.AppFluxState
import com.github.rougsig.rxflux.android.domain.todolist.generated.TodoListFluxState
import com.github.rougsig.rxflux.core.*
import toothpick.config.Module
import javax.inject.Inject

internal class AppStore @Inject constructor(
  private val todoListReducer: Reducer<TodoListFluxState, Action>,
  private val todoListActor: Middleware<TodoListFluxState>
) : Store<AppFluxState> by {
  createStore(
    AppFluxState.combineReducers(
      todoListReducer = todoListReducer
    ),
    wrapMiddleware(todoListActor) { it.todoList }
  )
}()

internal class StoreModule : Module() {
  init {
    this
      .bind(AppStore::class.java)
      .singletonInScope()
  }
}
