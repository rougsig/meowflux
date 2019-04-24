package com.github.rougsig.rxflux.android.ui.core.mvi

import com.github.rougsig.actionsdispatcher.runtime.BaseActionsReducer
import com.github.rougsig.rxflux.android.core.AppSchedulers
import com.github.rougsig.rxflux.android.core.skipFirstIf
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Completable
import io.reactivex.Observable

abstract class BasePresenter<V : MviView<VS>, VS, A: Any>(
  protected val schedulers: AppSchedulers,
  private val skipRenderOfInitialState: Boolean = false
) : MviBasePresenter<V, VS>() {

  private val outputActions = PublishRelay.create<A>()
  protected open val actionsReducer: BaseActionsReducer<VS, A>? = null

  final override fun bindIntents() {
    val stateChanges = Observable.merge(createIntents().plus(outputActions))
      .scan(Pair<VS, Command<A>?>(createInitialState(), null)) { s, a ->
        val (ps, _) = s
        val (ns, cmd) = reduceViewState(ps, a)
        Pair(ns, cmd)
      }
      .skipFirstIf(skipRenderOfInitialState)
      .observeOn(schedulers.ui)
      .doAfterNext { (_, cmd) ->
        val nextAction = cmd?.invoke()
        if (nextAction != null) outputActions.accept(nextAction)
      }
      .map { (vs, _) -> vs }
      .distinctUntilChanged()

    subscribeViewState(stateChanges) { view, viewState -> view.render(viewState) }
  }

  protected open fun reduceViewState(previousState: VS, action: A): Pair<VS, Command<A>?> {
    return actionsReducer?.reduce(previousState, action) ?: throw NotImplementedError()
  }

  protected abstract fun createIntents(): List<Observable<out A>>

  protected abstract fun createInitialState(): VS

  fun <T> bootstrapper(action: () -> Unit): Observable<T> {
    return Completable
      .fromAction(action)
      .toObservable()
  }

  fun <T> Observable<T>.withBootstrapper(action: () -> Unit): Observable<T> {
    return mergeWith(bootstrapper(action))
  }
}
