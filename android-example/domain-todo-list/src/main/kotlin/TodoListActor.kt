package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.actionOnSuccess
import com.github.rougsig.rxflux.android.core.toLceEventObservable
import com.github.rougsig.rxflux.android.domain.todolist.generated.TodoListFluxState
import com.github.rougsig.rxflux.android.repository.TodoListRepository
import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.dsl.ConfigurableActor
import javax.inject.Inject

sealed class TodoListActorAction : Action() {
  object LoadTodoList : TodoListActorAction()
  data class AddTodoItem(val text: String) : TodoListActorAction()
  data class RemoveTodoItem(val id: Long) : TodoListActorAction()
}

class TodoListActor @Inject constructor(
  private val repository: TodoListRepository,
  private val reducer: TodoListReducer
) : ConfigurableActor<TodoListFluxState, TodoListActorAction>() {
  init {
    concatMapActor<TodoListActorAction> {
      task(TodoListActorAction.AddTodoItem::class) { _, action ->
        repository
          .addTodoItem(action.text)
          .toLceEventObservable { TodoListReducerAction.UpdateAddItemState(it) }
          .actionOnSuccess { TodoListActorAction.LoadTodoList }
      }

      task(TodoListActorAction.RemoveTodoItem::class) { _, action ->
        repository
          .removeTodoItem(action.id)
          .toLceEventObservable { TodoListReducerAction.UpdateRemoveItemState(it) }
          .actionOnSuccess { TodoListActorAction.LoadTodoList }
      }

      task(TodoListActorAction.LoadTodoList::class) { _, _ ->
        repository
          .getTodoList()
          .toLceEventObservable { TodoListReducerAction.UpdateItemsState(it) }
      }
    }
  }
}

