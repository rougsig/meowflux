package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.domain.todolist.generated.TodoListFluxState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.reducer.TypedReducer
import javax.inject.Inject

sealed class TodoListReducerAction : Action() {
  data class UpdateItemsState(val state: LceState<List<TodoItem>>) : TodoListReducerAction()
  data class UpdateAddItemState(val state: LceState<Unit>) : TodoListReducerAction()
  data class UpdateRemoveItemState(val state: LceState<Unit>) : TodoListReducerAction()
}

class TodoListReducer @Inject constructor() : TypedReducer<TodoListFluxState, TodoListReducerAction>(
  TodoListFluxState(
    todoListItems = null,
    addTodoItem = null,
    removeTodoItem = null
  ),
  TodoListReducerAction::class
) {
  override fun reduceTyped(state: TodoListFluxState, action: TodoListReducerAction): TodoListFluxState {
    return when (action) {
      is TodoListReducerAction.UpdateItemsState ->
        state.setTodoListItems(action.state)
      is TodoListReducerAction.UpdateAddItemState ->
        state.setAddTodoItem(action.state)
      is TodoListReducerAction.UpdateRemoveItemState ->
        state.setRemoveTodoItem(action.state)
    }
  }
}
