package com.github.rougsig.rxflux.android.domain

import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.android.repository.TodoListRepository
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.CopyOnWriteArrayList

class TestTodoListRepository : TodoListRepository {
  @Volatile
  private var maxId = 1L
  private val todoList = CopyOnWriteArrayList<TodoItem>()

  init {
    todoList.add(TodoItem(
      id = 1L,
      text = "TodoItem 1"
    ))
  }

  override fun getTodoList(): Observable<List<TodoItem>> {
    return Observable
      .just<List<TodoItem>>(todoList)
  }

  override fun addTodoItem(todoDescription: String): Completable {
    return Completable
      .fromCallable {
        maxId += 1
        todoList.add(TodoItem(
          id = maxId,
          text = todoDescription
        ))
      }
  }

  override fun removeTodoItem(id: Long): Completable {
    return Completable
      .fromCallable {
        if (todoList.find { it.id == id }?.let(todoList::remove) != true)
          throw IllegalStateException("cannot remove todo with id: $id")
      }
  }
}
