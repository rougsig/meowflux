package com.github.rougsig.rxflux.core.middleware

import com.github.rougsig.rxflux.core.action.Action
import io.reactivex.ObservableSource
import kotlin.reflect.KClass

abstract class TypedActor<S : Any, A : Action>(
  val type: KClass<A>
) : Actor<S>() {
  @Suppress("UNCHECKED_CAST")
  final override fun apply(upstream: ActorUpstream<S, Action>): ObservableSource<Action> {
    return upstream
      .filter { (_, a) -> type.java.isInstance(a) }
      .map { (s, a) -> s to a as A }
      .compose(::applyTyped)
  }

  abstract fun applyTyped(upstream: ActorUpstream<S, A>): ObservableSource<Action>
}

@Suppress("FunctionName")
inline fun <S : Any, reified A : Action> TypedActor(
  crossinline actor: (upstream: ActorUpstream<S, A>) -> ObservableSource<Action>
): Actor<S> {
  return object : TypedActor<S, A>(A::class) {
    override fun applyTyped(upstream: ActorUpstream<S, A>): ObservableSource<Action> {
      return actor(upstream)
    }
  }
}
