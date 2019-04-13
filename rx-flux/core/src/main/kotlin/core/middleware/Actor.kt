package com.github.rougsig.rxflux.core.middleware

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.dispatcher.Dispatcher
import com.github.rougsig.rxflux.core.store.StateAccessor
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.ObservableSource

abstract class Actor<S : Any> : Middleware<S> {
  private val relay = PublishRelay.create<Action>()

  final override fun apply(accessor: StateAccessor<S>, dispatcher: Dispatcher): (Dispatcher) -> Dispatcher {
    relay
      .map { accessor to it }
      .compose(::apply)
      .subscribe(dispatcher::dispatch)

    return { next ->
      Dispatcher { action ->
        relay.accept(action)
        next.dispatch(action)
      }
    }
  }

  abstract fun apply(upstream: ActorUpstream<S, Action>): ObservableSource<Action>
}

internal class ActorImpl<S : Any, A : Action>(
  private val tasks: List<ActorTask<S, A>>,
  private val composer: ActorTaskComposer
) : Actor<S>() {
  override fun apply(upstream: ActorUpstream<S, Action>): ObservableSource<Action> {
    return upstream
      .publish { selector ->
        Observable.merge(
          tasks.map { task ->
            selector
              .filter { (_, action) -> task.type.java.isInstance(action) }
              .map { (accessor, action) -> task.run(accessor.getState(), task.type.java.cast(action)) }
          }
        )
      }
      .compose(composer)
  }
}

internal class ActorGroupImpl<S : Any>(
  private val actors: List<Actor<S>>
) : Actor<S>() {
  override fun apply(upstream: ActorUpstream<S, Action>): ObservableSource<Action> {
    return Observable.merge(actors.map { it.apply(upstream) })
  }
}
