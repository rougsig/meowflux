package com.github.rougsig.rxflux.android.ui.todolist

import android.view.View
import androidx.core.view.isVisible
import com.bluelinelabs.conductor.Controller
import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.core.instance
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.android.ui.core.ScopedMviController
import com.github.rougsig.rxflux.android.ui.todolist.di.ConnectModule
import kotlinx.android.synthetic.main.task_list_controller.*
import toothpick.config.Module

fun createTaskListController(): Controller = TaskListController()

internal class TaskListController :
  ScopedMviController<TaskListScreen.ViewState, TaskListScreen.View, TaskListPresenter>(),
  TaskListScreen.Renderer,
  TaskListScreen.View {
  private val epoxyController = TaskListEpoxyController()

  override fun createScopedConfig() = object : Config<TaskListScreen.ViewState> {
    override val viewLayoutResource = R.layout.task_list_controller
    override val diffDispatcher = ViewStateDiffDispatcher.Builder().target(this@TaskListController).build()
    override val presenterClass = TaskListPresenter::class.java
    override val screenModules = arrayOf<Module>(ConnectModule())
  }

  override fun initializeView(rootView: View) {
    task_list_recycler.setController(epoxyController)
  }

  override fun renderTodoList(state: LceState<List<TodoItem>>) {
    task_list_loading.isVisible = state.isLoading
    task_list_empty_list.isVisible = state.isContent && state.content.isNullOrEmpty()
    if (state.isContent) epoxyController.setData(state.asContent())
  }

  override fun createPresenter(): TaskListPresenter {
    return screenScope.instance()
  }
}
