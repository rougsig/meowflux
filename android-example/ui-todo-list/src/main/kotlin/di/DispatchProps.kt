package com.github.rougsig.rxflux.android.ui.todolist.di

import com.github.rougsig.rxflux.android.domain.app.generated.AppFluxState
import com.github.rougsig.rxflux.android.domain.todolist.loadTodoListAction
import com.github.rougsig.rxflux.core.store.Store
import javax.inject.Inject

internal interface DispatchProps {
  val loadTodoList: () -> Unit
}

internal class DispatchPropsImpl @Inject constructor(
  private val store: Store<AppFluxState>
) : DispatchProps {
  override val loadTodoList = { store.dispatch(loadTodoListAction()) }
}
