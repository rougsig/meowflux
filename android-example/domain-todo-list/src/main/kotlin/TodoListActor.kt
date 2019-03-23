package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.toLceEventObservable
import com.github.rougsig.rxflux.android.domain.todolist.generated.TodoListFluxState
import com.github.rougsig.rxflux.android.repository.TodoListRepository
import com.github.rougsig.rxflux.core.createSwitchActor

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
