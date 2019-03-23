package com.github.rougsig.rxflux.android.domain

import com.github.rougsig.rxflux.android.domain.generated.TodoListFluxState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.android.repository.TodoListRepository
import com.github.rougsig.rxflux.annotations.CreateFluxState
import com.github.rougsig.rxflux.core.Action
import com.github.rougsig.rxflux.core.createReducer
import com.github.rougsig.rxflux.core.createSwitchActor

@CreateFluxState
private interface TodoListState {
  val todoListItems: LceState<List<TodoItem>>?
  val addTodoItem: LceState<Unit>?
  val removeTodoItem: LceState<Unit>?
}

sealed class TodoListActorAction : Action() {
  object LoadTodoList : TodoListActorAction()
  data class AddTodoItem(val text: String) : TodoListActorAction()
  data class RemoveTodoItem(val id: Long) : TodoListActorAction()
}

private sealed class ReducerAction : Action() {
  data class UpdateItemsState(val state: LceState<List<TodoItem>>) : ReducerAction()
  data class UpdateAddItemState(val state: LceState<Unit>) : ReducerAction()
  data class UpdateRemoveItemState(val state: LceState<Unit>) : ReducerAction()
}

fun createTodoListActor(
  repository: TodoListRepository
) = createSwitchActor { _: TodoListFluxState, a: TodoListActorAction ->
  when (a) {
    is TodoListActorAction.LoadTodoList ->
      repository.getTodoList()
        .toLceEventObservable { ReducerAction.UpdateItemsState(it) }
    is TodoListActorAction.AddTodoItem ->
      repository.addTodoItem(a.text)
        .toLceEventObservable(
          actionOnSuccess = { TodoListActorAction.LoadTodoList },
          stateCreator = { ReducerAction.UpdateAddItemState(it) }
        )
    is TodoListActorAction.RemoveTodoItem ->
      repository.removeTodoItem(a.id)
        .toLceEventObservable(
          actionOnSuccess = { TodoListActorAction.LoadTodoList },
          stateCreator = { ReducerAction.UpdateRemoveItemState(it) }
        )
  }
}

val todoListReducer = createReducer(TodoListFluxState(
  todoListItems = null,
  addTodoItem = null,
  removeTodoItem = null
)) { s: TodoListFluxState, a: ReducerAction ->
  when (a) {
    is ReducerAction.UpdateItemsState ->
      s.setTodoListItems(a.state)
    is ReducerAction.UpdateAddItemState ->
      s.setAddTodoItem(a.state)
    is ReducerAction.UpdateRemoveItemState ->
      s.setRemoveTodoItem(a.state)
  }
}
