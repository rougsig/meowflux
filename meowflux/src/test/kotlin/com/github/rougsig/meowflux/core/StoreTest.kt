package com.github.rougsig.meowflux.core

import com.github.rougsig.meowflux.core.fakes.*
import com.github.rougsig.meowflux.core.fakes.CatCounterAction.Increment
import com.github.rougsig.meowflux.core.fakes.CatCounterAction.SetValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.Test
import java.util.*
import kotlin.concurrent.thread

@FlowPreview
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class StoreTest {

  @Test
  fun `on init should send init action`() {
    // Arrange
    val reducer = FakeReducer()
    BaseStore(
      initialState = Unit,
      reducer = reducer
    )

    // Act
    // no-op

    // Assert
    assertThat(reducer.actionHistory)
      .containsExactly(MeowFluxInit)
  }

  @Test
  fun `on dispatch action should be processed by reducer`() {
    // Arrange
    val store = BaseStore(
      initialState = CatCounter(),
      reducer = catCounterReducer
    )

    // Act
    store.dispatch(SetValue(10))

    // Assert
    assertThat(store.getState())
      .isEqualTo(CatCounter(catCount = 10))
  }

  @Test
  fun `dispatch from multiply thread should be not throw ConcurrentModificationException`() {
    // Arrange
    val store = BaseStore(
      initialState = CatCounter(),
      reducer = catCounterReducer
    )

    // Act
    (0..255).map {
      thread {
        store.dispatch(Increment)
      }
    }.forEach(Thread::join)

    // Assert
    assertThat(store.getState())
      .isEqualTo(CatCounter(catCount = 256))
  }

  @Test
  fun `updated state should be send to state flow`() {
    // Arrange
    val items = LinkedList<Any>()
    val store = BaseStore(
      initialState = CatCounter(),
      reducer = catCounterReducer
    )
    store.subscribe {
      items.add(it)
    }

    // Act
    store.dispatch(SetValue(10))

    // Assert
    assertThat(items)
      .containsExactly(CatCounter(catCount = 10))
  }

  @Test
  fun `on dispatch action should be processed by middleware`() {
    // Arrange
    val store = BaseStore(
      initialState = CatCounter(),
      reducer = catCounterReducer,
      middleware = listOf(duplicateMiddleware)
    )

    // Act
    store.dispatch(Increment)

    // Assert
    assertThat(store.getState())
      .isEqualTo(CatCounter(catCount = 2))
  }
}
