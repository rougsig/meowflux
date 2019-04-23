package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.domain.todolist.actor.TodoListActorImpl
import com.github.rougsig.rxflux.android.domain.todolist.reducer.TodoListReducerImpl
import com.github.rougsig.rxflux.android.domain.todolist.reducer.TodoListState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.core.store.Store
import io.reactivex.observers.TestObserver
import junit.framework.TestCase

class TestTodoListActor : TestCase() {

  fun testLoadTodoList() {
    val store = Store()

    val reducer = TodoListReducerImpl()
    val actor = TodoListActorImpl(reducer, TestTodoListRepository())

    store.addReducer(reducer)
    store.addActor(actor)

    val observer = TestObserver<TodoListState>()
    reducer.stateLive.subscribe(observer)

    store.dispatch(actor.loadTodoList())

    observer
      .assertNotComplete()
      .assertValues(
        TodoListState(
          todoListItems = null,
          addTodoItem = null,
          removeTodoItem = null
        ),
        TodoListState(
          todoListItems = LceState.Loading(),
          addTodoItem = null,
          removeTodoItem = null
        ),
        TodoListState(
          todoListItems = LceState.Content(listOf(
            TodoItem(
              id = 1,
              text = "TodoItem 1"
            )
          )),
          addTodoItem = null,
          removeTodoItem = null
        )
      )
  }

  fun testAddTodoItem() {
    val store = Store()

    val reducer = TodoListReducerImpl()
    val actor = TodoListActorImpl(reducer, TestTodoListRepository())

    store.addReducer(reducer)
    store.addActor(actor)

    val observer = TestObserver<TodoListState>()
    reducer.stateLive.subscribe(observer)

    store.dispatch(actor.addTodoItem(text = "Hello World"))

    observer
      .assertNotComplete()
      .assertValues(
        TodoListState(
          todoListItems = null,
          addTodoItem = null,
          removeTodoItem = null
        ),
        TodoListState(
          todoListItems = null,
          addTodoItem = LceState.Loading(),
          removeTodoItem = null
        ),
        TodoListState(
          todoListItems = null,
          addTodoItem = LceState.Content(Unit),
          removeTodoItem = null
        ),
        TodoListState(
          todoListItems = LceState.Loading(),
          addTodoItem = LceState.Content(Unit),
          removeTodoItem = null
        ),
        TodoListState(
          todoListItems = LceState.Content(
            listOf(
              TodoItem(
                id = 1L,
                text = "TodoItem 1"
              ),
              TodoItem(
                id = 2L,
                text = "Hello World"
              )
            )
          ),
          addTodoItem = LceState.Content(Unit),
          removeTodoItem = null
        )
      )
  }

  fun testRemoveTodoItem() {
    val store = Store()

    val reducer = TodoListReducerImpl()
    val actor = TodoListActorImpl(reducer, TestTodoListRepository())

    store.addReducer(reducer)
    store.addActor(actor)

    val observer = TestObserver<TodoListState>()
    reducer.stateLive.subscribe(observer)

    store.dispatch(actor.removeTodoItem(id = 1))

    observer
      .assertNotComplete()
      .assertValues(
        TodoListState(
          todoListItems = null,
          addTodoItem = null,
          removeTodoItem = null
        ),
        TodoListState(
          todoListItems = null,
          addTodoItem = null,
          removeTodoItem = LceState.Loading()
        ),
        TodoListState(
          todoListItems = null,
          addTodoItem = null,
          removeTodoItem = LceState.Content(Unit)
        ),
        TodoListState(
          todoListItems = LceState.Loading(),
          addTodoItem = null,
          removeTodoItem = LceState.Content(Unit)
        ),
        TodoListState(
          todoListItems = LceState.Content(emptyList()),
          addTodoItem = null,
          removeTodoItem = LceState.Content(Unit)
        )
      )
  }
}
