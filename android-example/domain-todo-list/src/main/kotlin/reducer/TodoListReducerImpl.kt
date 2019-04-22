package com.github.rougsig.rxflux.android.domain.todolist.reducer

import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.dsl.ConfigurableReducer
import javax.inject.Inject

private sealed class TodoListReducerAction
private data class UpdateItemsState(val state: LceState<List<TodoItem>>) : TodoListReducerAction()
private data class UpdateAddItemState(val state: LceState<Unit>) : TodoListReducerAction()
private data class UpdateRemoveItemState(val state: LceState<Unit>) : TodoListReducerAction()

internal class TodoListReducerImpl @Inject constructor() :
  ConfigurableReducer<TodoListState>(TodoListState()),
  TodoListReducer {

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

  override val todoList = createSelector { todoListItems }

  override fun updateItemsState(state: LceState<List<TodoItem>>) = createAction { UpdateItemsState(state) }
  override fun updateAddItemState(state: LceState<Unit>) = createAction { UpdateAddItemState(state) }
  override fun updateRemoveItemState(state: LceState<Unit>) = createAction { UpdateRemoveItemState(state) }
}
