package com.github.rougsig.rxflux.android.ui.todolist.di

import com.github.rougsig.rxflux.android.domain.app.generated.AppFluxState
import com.github.rougsig.rxflux.android.domain.todolist.todoList
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.core.Store
import io.reactivex.Observable
import javax.inject.Inject

internal interface StateProps {
  val todoList: Observable<List<TodoItem>>
}

internal class StatePropsImpl @Inject constructor(
  private val store: Store<AppFluxState>
) : StateProps {
  override val todoList = store.stateLive.todoList
}
