package com.github.rougsig.rxflux.android.domain

import com.github.rougsig.rxflux.android.core.instance
import com.github.rougsig.rxflux.android.domain.generated.AppFluxState
import com.github.rougsig.rxflux.android.domain.todolist.TodoListActor
import com.github.rougsig.rxflux.android.domain.todolist.todoListReducer
import com.github.rougsig.rxflux.core.Dispatcher
import com.github.rougsig.rxflux.core.connectToStore
import com.github.rougsig.rxflux.core.createStore
import com.github.rougsig.rxflux.core.wrapMiddleware
import io.reactivex.Observable

internal val store = createStore(
  AppFluxState.combineReducers(
    todoListReducer = todoListReducer
  ),
  wrapMiddleware(appScope.instance<TodoListActor>()) { it.todoList }
)

fun <SP : Any, DP : Any, R : Any> connect(
  mapStateToProps: (state: Observable<AppFluxState>) -> SP,
  mapDispatchToProps: (dispatcher: Dispatcher) -> DP,
  constructor: (SP, DP) -> R
): () -> R {
  return connectToStore(store, mapStateToProps, mapDispatchToProps, constructor)
}
