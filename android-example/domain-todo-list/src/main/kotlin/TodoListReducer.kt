package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.domain.todolist.generated.TodoListFluxState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.dsl.ConfigurableReducer
import javax.inject.Inject

sealed class TodoListReducerAction : Action() {
  data class UpdateItemsState(val state: LceState<List<TodoItem>>) : TodoListReducerAction()
  data class UpdateAddItemState(val state: LceState<Unit>) : TodoListReducerAction()
  data class UpdateRemoveItemState(val state: LceState<Unit>) : TodoListReducerAction()
}

class TodoListReducer @Inject constructor() : ConfigurableReducer<TodoListFluxState, TodoListReducerAction>(
  TodoListReducerAction::class,
  TodoListFluxState(
    todoListItems = null,
    addTodoItem = null,
    removeTodoItem = null
  )
) {
  init {
    mutator(TodoListReducerAction.UpdateItemsState::class) { state, action ->
      state.setTodoListItems(action.state)
    }
    mutator(TodoListReducerAction.UpdateAddItemState::class) { state, action ->
      state.setAddTodoItem(action.state)
    }
    mutator(TodoListReducerAction.UpdateRemoveItemState::class) { state, action ->
      state.setRemoveTodoItem(action.state)
    }
  }
}
