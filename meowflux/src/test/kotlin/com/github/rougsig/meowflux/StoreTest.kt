package com.github.rougsig.meowflux

import com.github.rougsig.meowflux.core.BaseStore
import com.github.rougsig.meowflux.core.MeowFluxInit
import com.github.rougsig.meowflux.fakes.CatCounter
import com.github.rougsig.meowflux.fakes.CatCounterAction.Increment
import com.github.rougsig.meowflux.fakes.CatCounterAction.SetValue
import com.github.rougsig.meowflux.fakes.FakeReducer
import com.github.rougsig.meowflux.fakes.catCounterReducer
import com.github.rougsig.meowflux.fakes.duplicateMiddleware
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
      storeScope = GlobalScope,
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
      storeScope = GlobalScope,
      reducer = catCounterReducer,
      initialState = CatCounter()
    )

    // Act
    runBlocking {
      store.dispatch(SetValue(10)).join()
    }

    // Assert
    assertThat(store.getState())
      .isEqualTo(CatCounter(catCount = 10))
  }

  @Test
  fun `dispatch from multiply thread should be not throw ConcurrentModificationException`() {
    // Arrange
    val store = BaseStore(
      storeScope = GlobalScope,
      reducer = catCounterReducer,
      initialState = CatCounter()
    )

    // Act
    repeat(32) {
      thread {
        runBlocking { store.dispatch(Increment) }
      }.join()
    }

    // Assert
    assertThat(store.getState())
      .isEqualTo(CatCounter(catCount = 32))
  }

  @Test
  fun `updated state should be send to state flow`() {
    // Arrange
    val store = BaseStore(
      storeScope = GlobalScope,
      reducer = catCounterReducer,
      initialState = CatCounter()
    )

    // Act
    store.dispatch(SetValue(10))

    // Assert
    val items = runBlocking {
      val items = LinkedList<Any>()
      store.stateFlow.take(1).collect { items.add(it) }
      items
    }

    assertThat(items)
      .containsExactly(CatCounter(catCount = 10))
  }

  @Test
  fun `on dispatch action should be processed by middleware`() {
    // Arrange
    val store = BaseStore(
      storeScope = GlobalScope,
      reducer = catCounterReducer,
      initialState = CatCounter(),
      middlewares = listOf(duplicateMiddleware)
    )

    // Act
    runBlocking {
      store.dispatch(Increment).join()
    }

    // Assert
    assertThat(store.getState())
      .isEqualTo(CatCounter(catCount = 2))
  }
}
