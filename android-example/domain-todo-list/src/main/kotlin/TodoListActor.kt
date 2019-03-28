package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.toLceEventObservable
import com.github.rougsig.rxflux.android.domain.todolist.generated.TodoListFluxState
import com.github.rougsig.rxflux.android.repository.TodoListRepository
import com.github.rougsig.rxflux.core.Action
import com.github.rougsig.rxflux.core.createSwitchActor

internal sealed class TodoListActorAction : Action() {
  object LoadTodoList : TodoListActorAction()
  data class AddTodoItem(val text: String) : TodoListActorAction()
  data class RemoveTodoItem(val id: Long) : TodoListActorAction()
}

fun createTodoListActor(
  repository: TodoListRepository
) = createSwitchActor { _: TodoListFluxState, a: TodoListActorAction ->
  when (a) {
    is TodoListActorAction.LoadTodoList ->
      repository.getTodoList()
        .toLceEventObservable { TodoListReducerAction.UpdateItemsState(it) }
    is TodoListActorAction.AddTodoItem ->
      repository.addTodoItem(a.text)
        .toLceEventObservable(
          actionOnSuccess = { TodoListActorAction.LoadTodoList },
          stateCreator = { TodoListReducerAction.UpdateAddItemState(it) }
        )
    is TodoListActorAction.RemoveTodoItem ->
      repository.removeTodoItem(a.id)
        .toLceEventObservable(
          actionOnSuccess = { TodoListActorAction.LoadTodoList },
          stateCreator = { TodoListReducerAction.UpdateRemoveItemState(it) }
        )
  }
}

fun loadTodoListAction(): Action = TodoListActorAction.LoadTodoList
fun addTodoItemAction(text: String): Action = TodoListActorAction.AddTodoItem(text)
fun removeTodoItemAction(id: Long): Action = TodoListActorAction.RemoveTodoItem(id)
