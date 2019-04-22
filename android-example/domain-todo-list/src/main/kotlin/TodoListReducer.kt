package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.dsl.ConfigurableReducer
import javax.inject.Inject

private sealed class TodoListReducerAction
private data class UpdateItemsState(val state: LceState<List<TodoItem>>) : TodoListReducerAction()
private data class UpdateAddItemState(val state: LceState<Unit>) : TodoListReducerAction()
private data class UpdateRemoveItemState(val state: LceState<Unit>) : TodoListReducerAction()

class TodoListReducer @Inject constructor() : ConfigurableReducer<TodoListState>(
  TodoListState(
    todoListItems = null,
    addTodoItem = null,
    removeTodoItem = null
  )
) {
  init {
    mutator(UpdateItemsState::class) { state, action ->
      state.copy(todoListItems = action.state)
    }
    mutator(UpdateAddItemState::class) { state, action ->
      state.copy(addTodoItem = action.state)
    }
    mutator(UpdateRemoveItemState::class) { state, action ->
      state.copy(removeTodoItem = action.state)
    }
  }

  fun updateItemsState(state: LceState<List<TodoItem>>) = createAction { UpdateItemsState(state) }
  fun updateAddItemState(state: LceState<Unit>) = createAction { UpdateAddItemState(state) }
  fun updateRemoveItemState(state: LceState<Unit>) = createAction { UpdateRemoveItemState(state) }
}
