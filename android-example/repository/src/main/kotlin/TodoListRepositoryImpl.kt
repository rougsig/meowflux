package com.github.rougsig.rxflux.android.repository

import com.github.rougsig.rxflux.android.enitity.TodoItem
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit

internal class TodoListRepositoryImpl : TodoListRepository {
  private val todoList = CopyOnWriteArrayList<TodoItem>()

  init {
    todoList.add(TodoItem(
      id = 1L,
      text = "TodoItem 1"
    ))
    todoList.add(TodoItem(
      id = 2L,
      text = "TodoItem 2"
    ))
    todoList.add(TodoItem(
      id = 3L,
      text = "TodoItem 3"
    ))
  }

  override fun getTodoList(): Observable<List<TodoItem>> {
    return Observable
      .just<List<TodoItem>>(todoList)
      .delay(2L, TimeUnit.SECONDS)
  }

  override fun addTodoItem(todoDescription: String): Completable {
    return Completable
      .fromCallable {
        todoList.add(TodoItem(
          id = System.currentTimeMillis(),
          text = todoDescription
        ))
      }
      .delay(2L, TimeUnit.SECONDS)
  }

  override fun removeTodoItem(id: Long): Completable {
    return Completable
      .fromCallable {
        if (todoList.find { it.id == id }?.let(todoList::remove) != true)
          throw IllegalStateException("cannot remove todo with id: $id")
      }
      .delay(2L, TimeUnit.SECONDS)
  }
}
