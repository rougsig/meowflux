package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.core.distinctFieldChanges
import com.github.rougsig.rxflux.android.domain.app.generated.AppFluxState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import io.reactivex.Observable

fun getTodoListChanges(stateLive: Observable<AppFluxState>): Observable<List<TodoItem>> =
  stateLive.distinctFieldChanges { it.todoList.todoListItems }
    .filter { it.isContent }
    .map { it.asContent() }

fun addTodoItemChanges(stateLive: Observable<AppFluxState>): Observable<LceState<Unit>> =
  stateLive.distinctFieldChanges { it.todoList.addTodoItem }

fun removeTodoItemChanges(stateLive: Observable<AppFluxState>): Observable<LceState<Unit>> =
  stateLive.distinctFieldChanges { it.todoList.removeTodoItem }
