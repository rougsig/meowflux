package com.github.rougsig.rxflux.android.domain.todolist

import com.github.rougsig.rxflux.android.core.LceState
import com.github.rougsig.rxflux.android.domain.todolist.generated.TodoListFluxState
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.github.rougsig.rxflux.core.createStore
import io.reactivex.observers.TestObserver
import junit.framework.TestCase

class TestTodoListActor : TestCase() {

  fun testLoadTodoList() {
    val store = createStore(TodoListReducer(), TodoListActor(TestTodoListRepository()))

    val observer = TestObserver<TodoListFluxState>()
    store.stateLive.subscribe(observer)

    store.dispatch(TodoListActorAction.LoadTodoList)

    observer
      .assertNotComplete()
      .assertValues(
        TodoListFluxState(
          todoListItems = LceState.Loading(),
          addTodoItem = null,
          removeTodoItem = null
        ),
        TodoListFluxState(
          todoListItems = LceState.Content(
            listOf(TodoItem(
                id = 1L,
                text = "TodoItem 1"
              ))
          ),
          addTodoItem = null,
          removeTodoItem = null
        )
      )
  }

  fun testAddTodoItem() {
    val store = createStore(TodoListReducer(), TodoListActor(TestTodoListRepository()))

    val observer = TestObserver<TodoListFluxState>()
    store.stateLive.subscribe(observer)

    store.dispatch(TodoListActorAction.AddTodoItem(text = "Hello World"))

    observer
      .assertNotComplete()
      .assertValues(
        TodoListFluxState(
          todoListItems = null,
          addTodoItem = LceState.Loading(),
          removeTodoItem = null
        ),
        TodoListFluxState(
          todoListItems = null,
          addTodoItem = LceState.Content(Unit),
          removeTodoItem = null
        ),
        TodoListFluxState(
          todoListItems = LceState.Loading(),
          addTodoItem = LceState.Content(Unit),
          removeTodoItem = null
        ),
        TodoListFluxState(
          todoListItems = LceState.Content(
            listOf(TodoItem(
                id = 1L,
                text = "TodoItem 1"
              ),
              TodoItem(
                id = 2L,
                text = "Hello World"
              ))
          ),
          addTodoItem = LceState.Content(Unit),
          removeTodoItem = null
        )
      )
  }

  fun testRemoveTodoItem() {
    val store = createStore(TodoListReducer(), TodoListActor(TestTodoListRepository()))

    val observer = TestObserver<TodoListFluxState>()
    store.stateLive.subscribe(observer)

    store.dispatch(TodoListActorAction.RemoveTodoItem(id = 1L))

    observer
      .assertNotComplete()
      .assertValues(
        TodoListFluxState(
          todoListItems = null,
          addTodoItem = null,
          removeTodoItem = LceState.Loading()
        ),
        TodoListFluxState(
          todoListItems = null,
          addTodoItem = null,
          removeTodoItem = LceState.Content(Unit)
        ),
        TodoListFluxState(
          todoListItems = LceState.Loading(),
          addTodoItem = null,
          removeTodoItem = LceState.Content(Unit)
        ),
        TodoListFluxState(
          todoListItems = LceState.Content(emptyList()),
          addTodoItem = null,
          removeTodoItem = LceState.Content(Unit)
        )
      )
  }
}
