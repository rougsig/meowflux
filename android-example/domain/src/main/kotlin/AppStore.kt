package com.github.rougsig.rxflux.android.domain

import com.github.rougsig.rxflux.android.core.instance
import com.github.rougsig.rxflux.android.domain.generated.AppFluxState
import com.github.rougsig.rxflux.android.domain.todolist.createTodoListActor
import com.github.rougsig.rxflux.android.domain.todolist.todoListReducer
import com.github.rougsig.rxflux.core.Action
import com.github.rougsig.rxflux.core.Dispatcher
import com.github.rougsig.rxflux.core.createStore
import com.github.rougsig.rxflux.core.wrapMiddleware
import io.reactivex.Observable

internal val store = createStore(
  AppFluxState.combineReducers(
    todoListReducer = todoListReducer
  ),
  wrapMiddleware(createTodoListActor(appScope.instance())) { it.todoList }
)

fun <SP, DP, R> connect(
  mapStateToProps: (state: Observable<AppFluxState>) -> SP,
  mapDispatchToProps: (dispatcher: Dispatcher) -> DP,
  constructor: (SP, DP) -> R
): () -> R {
  return { constructor(mapStateToProps(store.stateLive), mapDispatchToProps { a: Action -> store.dispatch(a) }) }
}
