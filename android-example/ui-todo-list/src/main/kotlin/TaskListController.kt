package com.github.rougsig.rxflux.android.ui.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller

class TaskListController : Controller() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    return inflater.inflate(R.layout.task_list_controller, container, false)
  }
}

