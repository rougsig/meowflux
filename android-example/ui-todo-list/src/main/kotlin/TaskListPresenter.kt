package com.github.rougsig.rxflux.android.ui.todolist

import com.github.rougsig.actionsdispatcher.annotations.ActionElement
import com.github.rougsig.rxflux.android.core.AppSchedulers
import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.android.ui.core.BasePresenter
import com.github.rougsig.rxflux.android.ui.core.command
import com.github.rougsig.rxflux.android.ui.todolist.di.DispatchProps
import com.github.rougsig.rxflux.android.ui.todolist.di.StateProps
import javax.inject.Inject

@ActionElement(state = TaskListScreen.ViewState::class)
internal sealed class ScreenAction

internal data class UpdateLceState(val state: LceState<List<TodoItem>>) : ScreenAction()

internal class TaskListPresenter @Inject constructor(
  schedulers: AppSchedulers,
  private val stateProps: StateProps,
  private val dispatchProps: DispatchProps
) : BasePresenter<TaskListScreen.View, TaskListScreen.ViewState, ScreenAction>(schedulers), ActionReceiver {
  override val actionsReducer = ActionsReducer.Builder().receiver(this).build()

  override fun createIntents() = listOf(
    stateProps
      .todoList
      .map(::UpdateLceState)
      .withBootstrapper(dispatchProps.loadTodoList)
  )

  override fun processUpdateLceState(
    previousState: TaskListScreen.ViewState,
    action: UpdateLceState
  ): Pair<TaskListScreen.ViewState, (() -> ScreenAction?)?> {
    return previousState.copy(state = action.state) to null
  }

  override fun createInitialState() = TaskListScreen.ViewState(
    state = LceState.Loading()
  )
}

