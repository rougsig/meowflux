package com.github.rougsig.rxflux.android.ui.todolist

import com.github.rougsig.actionsdispatcher.annotations.ActionElement
import com.github.rougsig.rxflux.android.core.AppSchedulers
import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.android.ui.core.mvi.BasePresenter
import com.github.rougsig.rxflux.android.ui.core.mvi.command
import com.github.rougsig.rxflux.android.ui.todolist.TaskListScreen.View
import com.github.rougsig.rxflux.android.ui.todolist.TaskListScreen.ViewState
import com.github.rougsig.rxflux.android.ui.todolist.di.DispatchProps
import com.github.rougsig.rxflux.android.ui.todolist.di.StateProps
import javax.inject.Inject

@ActionElement(state = ViewState::class)
internal sealed class ScreenAction

internal data class UpdateLceState(val state: LceState<List<TodoItem>>) : ScreenAction()
internal data class ShowTodoDetailsScreen(val itemId: Long) : ScreenAction()
internal object ShowCreateScreen : ScreenAction()

internal class TaskListPresenter @Inject constructor(
  schedulers: AppSchedulers,
  private val stateProps: StateProps,
  private val dispatchProps: DispatchProps
) : BasePresenter<View, ViewState, ScreenAction>(schedulers), ActionReceiver {
  override val actionsReducer = ActionsReducer.Builder().receiver(this).build()

  override fun createIntents() = listOf(
    stateProps
      .todoList
      .map(::UpdateLceState)
      .withBootstrapper(dispatchProps.loadTodoList),

    intent(View::showCreateTodoItemIntent)
      .map { ShowCreateScreen },

    intent(View::showTodoItemDetailsIntent)
      .map { ShowTodoDetailsScreen(it) }
  )

  override fun processUpdateLceState(
    previousState: ViewState,
    action: UpdateLceState
  ): Pair<ViewState, (() -> ScreenAction?)?> {
    return previousState.copy(state = action.state) to null
  }

  override fun processShowTodoDetailsScreen(
    previousState: ViewState,
    action: ShowTodoDetailsScreen
  ): Pair<ViewState, (() -> ScreenAction?)?> {
    return previousState to command { dispatchProps.showTodoItemDetailsScreen(action.itemId) }
  }

  override fun processShowCreateScreen(
    previousState: ViewState,
    action: ShowCreateScreen
  ): Pair<ViewState, (() -> ScreenAction?)?> {
    return previousState to command { dispatchProps.showCreateTodoItemScreen() }
  }

  override fun createInitialState() = ViewState(
    state = LceState.Loading()
  )
}

