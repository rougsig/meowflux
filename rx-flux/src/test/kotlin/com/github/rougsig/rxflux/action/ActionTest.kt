package com.github.rougsig.rxflux.action

import io.reactivex.observers.TestObserver
import org.testng.annotations.Test

class ActionTest {
  @Test
  fun `create event invoke method should pass value to observer`() {
    // Arrange
    val testObserver = TestObserver.create<Action>()
    val click = createAction<Int>()

    click.actions.subscribe(testObserver)
    // Act
    click(1)
    click(2)
    click(3)

    // Assert
    testObserver
      .assertNotComplete()
      .assertValues(
        click.createAction(1),
        click.createAction(2),
        click.createAction(3)
      )
  }
}
