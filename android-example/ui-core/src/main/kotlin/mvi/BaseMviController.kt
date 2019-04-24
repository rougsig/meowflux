package com.github.rougsig.rxflux.android.ui.core.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.dimsuz.diffdispatcher.DiffDispatcher
import com.hannesdorfmann.mosby3.MviController
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.jakewharton.rxrelay2.PublishRelay
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.*

abstract class BaseMviController<VS, V : MviView<VS>, P : MviPresenter<V, VS>>
  : MviController<V, P>, MviView<VS>,
  LayoutContainer {

  interface Config<VS> {
    val viewLayoutResource: Int
    val diffDispatcher: DiffDispatcher<VS>?
    val clearPreviousStateOnDestroy: Boolean
      get() = true
  }

  protected val config: Config<VS> by lazy(LazyThreadSafetyMode.NONE) { createConfig() }
  protected val eventsRelay: PublishRelay<Pair<Int, Any>> = PublishRelay.create()
  protected var previousViewState: VS? = null

  private var bindPropsRootView: View? = null

  protected abstract fun createConfig(): Config<VS>
  override val containerView: View? get() = bindPropsRootView

  constructor()
  constructor(args: Bundle) : super(args)

  final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    val rootView = inflater.inflate(config.viewLayoutResource, container, false)
    bindPropsRootView = rootView
    initializeView(rootView)
    return rootView
  }

  abstract fun initializeView(rootView: View)

  override fun onDestroyView(view: View) {
    clearFindViewByIdCache()
    bindPropsRootView = null
    super.onDestroyView(view)
    if (config.clearPreviousStateOnDestroy) {
      previousViewState = null
    }
  }

  final override fun render(viewState: VS) {
    renderViewState(viewState)
    previousViewState = viewState
  }

  protected open fun renderViewState(viewState: VS) {
    config.diffDispatcher?.dispatch(viewState, previousViewState)
  }
}

