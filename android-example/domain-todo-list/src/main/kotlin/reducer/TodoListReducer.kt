package com.github.rougsig.rxflux.android.domain.todolist.reducer

import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.reducer.Reducer

interface TodoListReducer : Reducer<TodoListState> {
  fun updateItemsState(state: LceState<List<TodoItem>>): Action

  fun updateAddItemState(state: LceState<Unit>): Action

  fun updateRemoveItemState(state: LceState<Unit>): Action

  val todoList: (TodoListState) -> LceState<List<TodoItem>>?
}
