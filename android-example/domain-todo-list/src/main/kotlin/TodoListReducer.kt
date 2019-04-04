package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.domain.todolist.generated.TodoListFluxState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.core.Action
import com.github.rougsig.rxflux.core.Reducer
import com.github.rougsig.rxflux.core.safeReducer
import javax.inject.Inject

internal sealed class TodoListReducerAction : Action() {
  data class UpdateItemsState(val state: LceState<List<TodoItem>>) : TodoListReducerAction()
  data class UpdateAddItemState(val state: LceState<Unit>) : TodoListReducerAction()
  data class UpdateRemoveItemState(val state: LceState<Unit>) : TodoListReducerAction()
}

class TodoListReducer @Inject constructor() : Reducer<TodoListFluxState, Action> by {
  safeReducer<TodoListFluxState, TodoListReducerAction>(
    TodoListFluxState(
      todoListItems = null,
      addTodoItem = null,
      removeTodoItem = null
    )
  ) { s, a ->
    when (a) {
      is TodoListReducerAction.UpdateItemsState ->
        s.setTodoListItems(a.state)
      is TodoListReducerAction.UpdateAddItemState ->
        s.setAddTodoItem(a.state)
      is TodoListReducerAction.UpdateRemoveItemState ->
        s.setRemoveTodoItem(a.state)
    }
  }
}()
