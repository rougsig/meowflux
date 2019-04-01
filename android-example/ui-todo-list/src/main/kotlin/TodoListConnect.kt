package com.github.rougsig.rxflux.android.ui.todolist

import com.github.rougsig.rxflux.android.domain.app.connect
import com.github.rougsig.rxflux.android.domain.app.generated.AppFluxState
import com.github.rougsig.rxflux.android.domain.todolist.getTodoListChanges
import com.github.rougsig.rxflux.android.domain.todolist.loadTodoListAction
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.core.Dispatcher
import io.reactivex.Observable

internal fun mapStateToProps(state: Observable<AppFluxState>): StateProps {
  return object : StateProps {
    override val todoList: Observable<List<TodoItem>> = getTodoListChanges(state)
  }
}

internal fun mapDispatchToProps(dispatcher: Dispatcher): DispatchProps {
  return object : DispatchProps {
    override val loadTodoList: () -> Unit = { dispatcher(loadTodoListAction()) }
  }
}

internal class TodoListConnect(sp: StateProps, dp: DispatchProps) : StateProps by sp, DispatchProps by dp

internal val todoListConnect = connect(
  ::mapStateToProps,
  ::mapDispatchToProps,
  ::TodoListConnect
)
