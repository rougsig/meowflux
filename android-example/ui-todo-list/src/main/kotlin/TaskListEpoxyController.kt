package com.github.rougsig.rxflux.android.ui.todolist

import com.airbnb.epoxy.TypedEpoxyController
import com.github.rougsig.rxflux.android.enitity.TodoItem

internal class TaskListEpoxyController : TypedEpoxyController<List<TodoItem>>() {
  override fun buildModels(data: List<TodoItem>) {
    data.forEach {
      taskListItem {
        id(it.id)
        text(it.text)
      }
    }
  }
}
