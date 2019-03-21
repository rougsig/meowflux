package com.github.rougsig.rxflux.android.repository

import com.github.rougsig.rxflux.android.enitity.TodoItem
import io.reactivex.Completable
import io.reactivex.Observable

interface TodoListRepository {
  fun getTodoList(): Observable<List<TodoItem>>

  fun addTodoItem(todoDescription: String): Completable

  fun removeTodoItem(id: Long): Completable
}
