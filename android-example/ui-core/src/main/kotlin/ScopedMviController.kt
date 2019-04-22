package com.github.rougsig.rxflux.android.ui.core

import android.content.Context
import android.os.Bundle
import com.github.rougsig.rxflux.android.core.APP_SCOPE_NAME
import com.github.rougsig.rxflux.android.core.ToothpickEmptyModuleBindings
import com.github.rougsig.rxflux.android.core.ToothpickModuleBindings
import com.github.rougsig.rxflux.android.core.instance
import com.github.rougsig.rxflux.core.actor.Actor
import com.github.rougsig.rxflux.core.reducer.Reducer
import com.github.rougsig.rxflux.core.store.Store
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module

abstract class ScopedMviController<VS, V : MviView<VS>, P : MviPresenter<V, VS>> : BaseMviController<VS, V, P> {

  interface Config<VS> : BaseMviController.Config<VS> {
    val presenterClass: Class<*>
    val screenScopeName: String? get() = null

    val screenModules: Array<Module> get() = emptyArray()
    val screenBindings: ToothpickModuleBindings get() = ToothpickEmptyModuleBindings

    val screenReducers: List<Class<out Reducer<*>>> get() = emptyList()
    val screenActors: List<Class<out Actor>> get() = emptyList()
  }

  final override fun createConfig(): BaseMviController.Config<VS> = createScopedConfig()
  abstract fun createScopedConfig(): BaseMviController.Config<VS>

  protected lateinit var screenScope: Scope
    private set

  constructor()
  constructor(args: Bundle) : super(args)

  override fun onContextAvailable(context: Context) {
    super.onContextAvailable(context)
    val scopedConfig = config as Config<VS>

    val scopeName = scopedConfig.screenScopeName ?: this.javaClass.simpleName + System.currentTimeMillis().toString()
    screenScope = Toothpick.openScopes(APP_SCOPE_NAME, scopeName)

    val screenModule = ScreenModule(scopedConfig.presenterClass, scopedConfig.screenBindings)
    screenScope.installModules(screenModule, *scopedConfig.screenModules)

    val store = screenScope.instance<Store>()
    scopedConfig.screenReducers.forEach { reducer ->
      store.addReducer(screenScope.getInstance(reducer))
    }
    scopedConfig.screenActors.forEach { actor ->
      store.addActor(screenScope.getInstance(actor))
    }
  }

  override fun onDestroy() {
    super.onDestroy()

    val scopedConfig = config as Config<VS>

    val store = screenScope.instance<Store>()
    scopedConfig.screenReducers.forEach { reducer ->
      store.removeReducer(screenScope.getInstance(reducer))
    }
    scopedConfig.screenActors.forEach { actor ->
      store.removeActor(screenScope.getInstance(actor))
    }

    Toothpick.closeScope(screenScope.name)
  }

  private class ScreenModule(
    presenterClass: Class<*>,
    additionalBindings: ToothpickModuleBindings
  ) : Module() {
    init {
      bind(presenterClass)
      additionalBindings.bindInto(this)
    }
  }
}
