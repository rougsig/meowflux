package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.core.distinctFieldChanges
import com.github.rougsig.rxflux.android.domain.app.generated.AppFluxState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import io.reactivex.Observable

val Observable<AppFluxState>.todoList: Observable<List<TodoItem>>
  get() {
    return this
      .distinctFieldChanges { it.todoList.todoListItems }
      .filter { it.isContent }
      .map { it.asContent() }
  }

val Observable<AppFluxState>.addTodoItem: Observable<LceState<Unit>>
  get() {
    return this
      .distinctFieldChanges { it.todoList.addTodoItem }
  }

val Observable<AppFluxState>.removeTodoItem: Observable<LceState<Unit>>
  get() {
    return this
      .distinctFieldChanges { it.todoList.removeTodoItem }
  }
