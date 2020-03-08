package com.github.rougsig.meowflux.android.demo.app

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import com.github.rougsig.meowflux.android.connect
import com.github.rougsig.meowflux.android.demo.store.root.CounterAction
import com.github.rougsig.meowflux.android.demo.store.root.RootState
import com.github.rougsig.meowflux.android.diff
import com.github.rougsig.meowflux.android.observe
import com.github.rougsig.meowflux.core.Dispatcher
import com.squareup.contour.ContourLayout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.takeWhile

data class StateProps(
  val count: Int
)

data class DispatchProps(
  val increment: () -> Unit,
  val decrement: () -> Unit
)

class CounterView(
  context: Context,
  stateProps: Flow<StateProps>,
  dispatchProps: DispatchProps
) : ContourLayout(context) {
  private val incrementButton = Button(context).apply {
    text = "Increment"
    applyLayout(
      x = leftTo { parent.left() }.rightTo { parent.right() },
      y = topTo { parent.top() }
    )

    setOnClickListener { dispatchProps.increment() }
  }

  private val counter = TextView(context).apply {
    setTextColor(Color.BLACK)
    gravity = Gravity.CENTER
    textSize = 32f
    applyLayout(
      x = leftTo { parent.left() }.rightTo { parent.right() },
      y = topTo { incrementButton.bottom() }
    )
  }

  private val decrementButton = Button(context).apply {
    text = "Decrement"
    applyLayout(
      x = leftTo { parent.left() }.rightTo { parent.right() },
      y = topTo { counter.bottom() }
    )

    setOnClickListener { dispatchProps.decrement() }
  }

  init {
    stateProps
      .observe(this) {
      diff(StateProps::count) { sp ->
        counter.text = sp.count.toString()
      }
    }
  }
}

private fun mapStateToProps(state: RootState, context: Context): StateProps {
  return StateProps(state.count)
}

private fun mapDispatchToProps(dispatcher: Dispatcher): DispatchProps {
  return DispatchProps(
    increment = { dispatcher(CounterAction.Increment) },
    decrement = { dispatcher(CounterAction.Decrement) }
  )
}

val counterView = connect(::CounterView, ::mapStateToProps, ::mapDispatchToProps)
