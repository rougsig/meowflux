package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.actionOnSuccess
import com.github.rougsig.rxflux.android.core.toLceEventObservable
import com.github.rougsig.rxflux.android.repository.TodoListRepository
import com.github.rougsig.rxflux.dsl.ConfigurableActorGroup
import javax.inject.Inject

private sealed class TodoListActorAction
private object LoadTodoList : TodoListActorAction()
private data class AddTodoItem(val text: String) : TodoListActorAction()
private data class RemoveTodoItem(val id: Long) : TodoListActorAction()

class TodoListActor @Inject constructor(
  private val reducer: TodoListReducer,
  private val repository: TodoListRepository
) : ConfigurableActorGroup() {
  init {
    concatMapActor {
      task(AddTodoItem::class) { action ->
        repository
          .addTodoItem(action.text)
          .toLceEventObservable { reducer.updateAddItemState(it) }
          .actionOnSuccess { loadTodoList() }
      }

      task(RemoveTodoItem::class) { action ->
        repository
          .removeTodoItem(action.id)
          .toLceEventObservable { reducer.updateRemoveItemState(it) }
          .actionOnSuccess { loadTodoList() }
      }

      task(LoadTodoList::class) { _ ->
        repository
          .getTodoList()
          .toLceEventObservable { reducer.updateItemsState(it) }
      }
    }
  }

  fun loadTodoList() = createAction { LoadTodoList }
  fun addTodoItem(text: String) = createAction { AddTodoItem(text) }
  fun removeTodoItem(id: Long) = createAction { RemoveTodoItem(id) }
}

