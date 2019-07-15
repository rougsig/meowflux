package com.github.rougsig.rxflux.store

import com.github.rougsig.rxflux.action.createAction
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.testng.annotations.Test

class StoreTest {
  @Test
  fun `bind action should invoke handler on handle event`() {
    // Arrange
    val testObserver = TestObserver.create<Int>()
    val click = createAction<Unit>()
    val click2 = createAction<Unit>()
    val clickCounter = createStore(0)
      .bindAction(click) { s, _ -> s + 1 }
      .bindAction(click2) { s, _ -> s + 10 }

    clickCounter.stateLive.subscribe(testObserver)

    // Act
    click(Unit)
    click2(Unit)
    click(Unit)

    // Assert
    testObserver
      .assertNotComplete()
      .assertValues(0, 1, 11, 12)
  }

  @Test
  fun `bind effect should invoke handler on handle event`() {
    // Arrange
    val testObserver = TestObserver.create<Int>()
    val click = createAction<Unit>()
    val click2 = createAction<Unit>()
    val clickCounter = createStore(0)
      .bindEffect(click) { s, _ ->
        click2(Unit)
        Observable.just(click2.createAction(Unit), click2.createAction(Unit), click2.createAction(Unit))
      }
      .bindAction(click2) { s, _ -> s + 10 }

    clickCounter.stateLive.subscribe(testObserver)

    // Act
    click(Unit)

    // Assert
    testObserver
      .assertNotComplete()
      .assertValues(0, 10, 20, 30, 40)
  }
}
