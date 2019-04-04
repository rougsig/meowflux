package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.toLceEventObservable
import com.github.rougsig.rxflux.android.domain.todolist.generated.TodoListFluxState
import com.github.rougsig.rxflux.android.repository.TodoListRepository
import com.github.rougsig.rxflux.core.Action
import com.github.rougsig.rxflux.core.Middleware
import com.github.rougsig.rxflux.core.createSwitchActor
import javax.inject.Inject

sealed class TodoListActorAction : Action() {
  object LoadTodoList : TodoListActorAction()
  data class AddTodoItem(val text: String) : TodoListActorAction()
  data class RemoveTodoItem(val id: Long) : TodoListActorAction()
}

class TodoListActor @Inject constructor(repository: TodoListRepository) : Middleware<TodoListFluxState> by {
  createSwitchActor<TodoListFluxState, TodoListActorAction> { state, action ->
    when (action) {
      is TodoListActorAction.LoadTodoList ->
        repository.getTodoList()
          .toLceEventObservable { TodoListReducerAction.UpdateItemsState(it) }
      is TodoListActorAction.AddTodoItem ->
        repository.addTodoItem(action.text)
          .toLceEventObservable(
            actionOnSuccess = { TodoListActorAction.LoadTodoList },
            stateCreator = { TodoListReducerAction.UpdateAddItemState(it) }
          )
      is TodoListActorAction.RemoveTodoItem ->
        repository.removeTodoItem(action.id)
          .toLceEventObservable(
            actionOnSuccess = { TodoListActorAction.LoadTodoList },
            stateCreator = { TodoListReducerAction.UpdateRemoveItemState(it) }
          )
    }
  }
}()
