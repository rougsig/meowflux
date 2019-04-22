package com.github.rougsig.rxflux.android.ui.todolist.di

import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.domain.todolist.reducer.TodoListReducer
import com.github.rougsig.rxflux.android.enitity.TodoItem
import io.reactivex.Observable
import javax.inject.Inject

internal interface StateProps {
  val todoList: Observable<LceState<List<TodoItem>>>
}

internal class StatePropsImpl @Inject constructor(
  private val reducer: TodoListReducer
) : StateProps {
  override val todoList = reducer.select(reducer.todoList)
}
