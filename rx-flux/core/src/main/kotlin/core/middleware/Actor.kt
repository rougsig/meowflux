package com.github.rougsig.rxflux.core.middleware

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.dispatcher.Dispatcher
import com.github.rougsig.rxflux.core.store.StateAccessor
import com.jakewharton.rxrelay2.PublishRelay
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

internal class ActorImpl<S : Any>(
  private val tasks: List<ActorTask<S, Action>>,
  private val composer: ActorTaskComposer<S>
) : Actor<S>() {
  override fun apply(upstream: ActorUpstream<S, Action>): ObservableSource<Action> {
    return composer.compose(upstream, tasks)
  }
}

internal class ActorGroupImpl<S : Any>(
  private val actors: List<Actor<S>>
) : Actor<S>() {
  override fun apply(upstream: ActorUpstream<S, Action>): ObservableSource<Action> {
    return upstream
      .flatMapIterable { actors.map { it.apply(upstream) } }
      .concatMap { it }
  }
}
