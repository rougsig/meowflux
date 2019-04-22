package com.github.rougsig.rxflux.core.middleware

import com.github.rougsig.rxflux.core.action.Action
import io.reactivex.Observable
import io.reactivex.ObservableSource
import kotlin.reflect.KClass

interface ActorTask<A : Any> {
  fun run(action: A): ObservableSource<Action<*>>
}

internal class ActorTaskImpl<A : Any>(
  val type: KClass<A>,
  private val task: (A) -> ObservableSource<Action<*>>
) : ActorTask<Any> {
  override fun run(action: Any): ObservableSource<Action<*>> {
    return if (type.java.isInstance(action)) {
      task(type.java.cast(action))
    } else {
      Observable.empty()
    }
  }
}
