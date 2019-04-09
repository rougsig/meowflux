package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.toLceEventObservable
import com.github.rougsig.rxflux.android.domain.todolist.generated.TodoListFluxState
import com.github.rougsig.rxflux.android.repository.TodoListRepository
import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.middleware.ActorUpstream
import com.github.rougsig.rxflux.core.middleware.TypedActor
import com.github.rougsig.rxflux.core.middleware.TypedActorTask
import com.github.rougsig.rxflux.core.middleware.concatTasks
import io.reactivex.ObservableSource
import javax.inject.Inject

sealed class TodoListActorAction : Action() {
  object LoadTodoList : TodoListActorAction()
  data class AddTodoItem(val text: String) : TodoListActorAction()
  data class RemoveTodoItem(val id: Long) : TodoListActorAction()
}

class TodoListActor @Inject constructor(
  private val repository: TodoListRepository
) : TypedActor<TodoListFluxState, TodoListActorAction>(TodoListActorAction::class) {

  private val addItemTask = TypedActorTask<TodoListFluxState, TodoListActorAction.AddTodoItem> { _, action ->
    repository
      .addTodoItem(action.text)
      .toLceEventObservable(
        { TodoListActorAction.LoadTodoList },
        { TodoListReducerAction.UpdateAddItemState(it) }
      )
  }

  private val removeItemTask = TypedActorTask<TodoListFluxState, TodoListActorAction.RemoveTodoItem> { _, action ->
    repository
      .removeTodoItem(action.id)
      .toLceEventObservable(
        { TodoListActorAction.LoadTodoList },
        { TodoListReducerAction.UpdateRemoveItemState(it) }
      )
  }

  private val loadItemsTask = TypedActorTask<TodoListFluxState, TodoListActorAction.LoadTodoList> { _, _ ->
    repository
      .getTodoList()
      .toLceEventObservable { TodoListReducerAction.UpdateItemsState(it) }
  }

  override fun applyTyped(upstream: ActorUpstream<TodoListFluxState, TodoListActorAction>): ObservableSource<Action> {
    return upstream.concatTasks(
      addItemTask,
      removeItemTask,
      loadItemsTask
    )
  }
}
