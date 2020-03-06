package com.github.rougsig.meowflux.android

import android.view.View
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

fun <S : Any> Flow<S>.observe(view: View, init: StateDiff<S>.() -> Unit) {
  val diff = StateDiff<S>().also(init)
  runBlocking { diff.update(first()) }
  view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
    var scope: CoroutineScope? = null
    override fun onViewAttachedToWindow(v: View?) {
      scope = MainScope().also { scope ->
        scope.launch {
          collect {
            if (isActive && view.isAttachedToWindow) diff.update(it)
          }
        }
      }
    }

    override fun onViewDetachedFromWindow(v: View?) {
      scope?.cancel()
      scope = null
    }
  })
}
