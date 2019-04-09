package com.github.rougsig.rxflux.core.middleware

import com.github.rougsig.rxflux.core.action.Action
import io.reactivex.Observable
import io.reactivex.ObservableSource
import kotlin.reflect.KClass

abstract class TypedActorTask<S : Any, A : Action>(
  val type: KClass<A>
) : ActorTask<S> {
  @Suppress("UNCHECKED_CAST")
  final override fun apply(state: S, action: Action): ObservableSource<Action> {
    return if (type.java.isInstance(action)) {
      applyTyped(state, action as A)
    } else {
      Observable.empty()
    }

  }

  abstract fun applyTyped(state: S, action: A): ObservableSource<Action>
}

@Suppress("FunctionName")
inline fun <S : Any, reified A : Action> TypedActorTask(
  crossinline task: (state: S, action: A) -> ObservableSource<Action>
): ActorTask<S> {
  return object : TypedActorTask<S, A>(A::class) {
    override fun applyTyped(state: S, action: A): ObservableSource<Action> {
      return task(state, action)
    }
  }
}
