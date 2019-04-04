package com.github.rougsig.rxflux.android.ui.todolist

import com.github.dimsuz.diffdispatcher.annotations.DiffElement
import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.android.ui.core.MviView

internal interface TaskListScreen {
  interface View : MviView<ViewState>

  @DiffElement(Renderer::class)
  data class ViewState(
    val state: LceState<List<TodoItem>>
  )

  interface Renderer {
    fun renderTodoList(state: LceState<List<TodoItem>>)
  }
}
