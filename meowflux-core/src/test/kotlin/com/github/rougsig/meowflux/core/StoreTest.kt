package com.github.rougsig.meowflux.core

import com.github.rougsig.meowflux.fakes.CatCounter
import com.github.rougsig.meowflux.fakes.CatCounterAction.Increment
import com.github.rougsig.meowflux.fakes.CatCounterAction.SetValue
import com.github.rougsig.meowflux.fakes.FakeReducer
import com.github.rougsig.meowflux.fakes.catCounterReducer
import com.github.rougsig.meowflux.fakes.duplicateMiddleware
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.Test
import kotlin.concurrent.thread

@FlowPreview
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class StoreTest : CoroutineScope by GlobalScope {

  @Test
  fun `on init should send init action`() {
    // Arrange
    val reducer = FakeReducer()
    Store(
      initialState = Unit,
      reducer = reducer
    )

    // Act
    // no-op

    // Assert
    assertThat(reducer.actionHistory)
      .containsExactly(StoreInit)
  }

  @Test
  fun `on dispatch action should be processed by reducer`() {
    // Arrange
    val store = Store(
      initialState = CatCounter(),
      reducer = catCounterReducer
    )

    // Act
    runBlocking {
      store.dispatch(SetValue(10)).join()
    }

    // Assert
    assertThat(store.state)
      .isEqualTo(CatCounter(catCount = 10))
  }

  @Test
  fun `dispatch from multiply thread should be processed correctly`() {
    // Arrange
    val store = Store(
      initialState = CatCounter(),
      reducer = catCounterReducer,
      middleware = listOf(duplicateMiddleware)
    )

    // Act
    (0 until 64).map {
      thread {
        runBlocking { store.dispatch(Increment).join() }
      }
    }.forEach(Thread::join)

    // Assert
    assertThat(store.state)
      .isEqualTo(CatCounter(catCount = 128))
  }

  @Test
  fun `updated state should be send to state flow`() {
    // Arrange
    val store = Store(
      initialState = CatCounter(),
      reducer = catCounterReducer
    )

    // Act
    runBlocking {
      store.dispatch(SetValue(10)).join()
    }

    // Assert
    val items = runBlocking {
      store.stateFlow.take(1).toList()
    }

    assertThat(items)
      .containsExactly(CatCounter(catCount = 10))
  }

  @Test
  fun `on dispatch action should be processed by middleware`() {
    // Arrange
    val store = Store(
      initialState = CatCounter(),
      reducer = catCounterReducer,
      middleware = listOf(duplicateMiddleware)
    )

    // Act
    runBlocking {
      store.dispatch(Increment).join()
    }

    // Assert
    assertThat(store.state)
      .isEqualTo(CatCounter(catCount = 2))
  }
}
