package com.github.rougsig.rxflux.event

import io.reactivex.observers.TestObserver
import org.testng.annotations.Test

class EventTest {
  @Test
  fun `create event invoke method should pass value to observer`() {
    // Arrange
    val testObserver = TestObserver.create<Int>()
    val click = createEvent<Int>()

    click.subscribe(testObserver)
    // Act
    click(1)
    click(2)
    click(3)

    // Assert
    testObserver
      .assertNotComplete()
      .assertValues(1, 2, 3)
  }
}
