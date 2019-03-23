package com.github.rougsig.rxflux.android.domain

import com.github.rougsig.rxflux.android.core.instance
import com.github.rougsig.rxflux.android.domain.generated.AppFluxState
import com.github.rougsig.rxflux.android.domain.todolist.createTodoListActor
import com.github.rougsig.rxflux.android.domain.todolist.todoListReducer
import com.github.rougsig.rxflux.core.createStore
import com.github.rougsig.rxflux.core.wrapMiddleware

internal val store = createStore(
  AppFluxState.combineReducers(
    todoListReducer = todoListReducer
  ),
  wrapMiddleware(createTodoListActor(appScope.instance())) { it.todoList }
)
