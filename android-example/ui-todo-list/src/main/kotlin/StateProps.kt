package com.github.rougsig.rxflux.android.ui.todolist

import com.github.rougsig.rxflux.android.enitity.TodoItem
import io.reactivex.Observable

internal interface StateProps {
  val todoList: Observable<List<TodoItem>>
}
