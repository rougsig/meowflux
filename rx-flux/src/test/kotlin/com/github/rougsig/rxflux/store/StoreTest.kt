package com.github.rougsig.rxflux.store

import com.github.rougsig.rxflux.event.createEvent
import io.reactivex.observers.TestObserver
import org.testng.annotations.Test

class StoreTest {
  @Test
  fun `bind method should invoke block on handle event`() {
    // Arrange
    val testObserver = TestObserver.create<Int>()
    val click = createEvent<Unit>()
    val click2 = createEvent<Unit>()
    val clickCounter = createStore(0)
      .bind(click) { s, _ -> s + 1 }
      .bind(click2) { s, _ -> s + 10 }

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
}
