package com.github.rougsig.rxflux.android.domain.todolist

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
  private val repository: TodoListRepository
) : ConfigurableActor<TodoListFluxState, TodoListActorAction>() {
  init {
    concatMapActor<TodoListActorAction> {
      task(TodoListActorAction.AddTodoItem::class) { _, action ->
        repository
          .addTodoItem(action.text)
          .toLceEventObservable(
            { TodoListActorAction.LoadTodoList },
            { TodoListReducerAction.UpdateAddItemState(it) }
          )
      }

      task(TodoListActorAction.RemoveTodoItem::class) { _, action ->
        repository
          .removeTodoItem(action.id)
          .toLceEventObservable(
            { TodoListActorAction.LoadTodoList },
            { TodoListReducerAction.UpdateRemoveItemState(it) }
          )
      }

      task(TodoListActorAction.LoadTodoList::class) { _, _ ->
        repository
          .getTodoList()
          .toLceEventObservable { TodoListReducerAction.UpdateItemsState(it) }
      }
    }
  }
}
