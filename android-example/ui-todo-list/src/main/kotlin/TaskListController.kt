package com.github.rougsig.rxflux.android.ui.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.github.rougsig.rxflux.android.core.APP_SCOPE_NAME
import com.github.rougsig.rxflux.android.core.instance
import com.github.rougsig.rxflux.android.ui.todolist.di.DispatchProps
import com.github.rougsig.rxflux.android.ui.todolist.di.StateProps
import com.github.rougsig.rxflux.android.ui.todolist.di.TodoListConnectModule
import toothpick.Toothpick

class TaskListController : Controller() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    return inflater.inflate(R.layout.task_list_controller, container, false)
  }

  init {
    val screenScope = Toothpick.openScopes(APP_SCOPE_NAME, this.javaClass.simpleName)
    screenScope.installModules(TodoListConnectModule())

    val dispatchProps: DispatchProps = screenScope.instance()
    val stateProps: StateProps = screenScope.instance()

    println("initialized: $dispatchProps, $stateProps")
  }
}
