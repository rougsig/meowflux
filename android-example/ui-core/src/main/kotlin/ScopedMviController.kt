package com.github.rougsig.rxflux.android.ui.core

import android.content.Context
import android.os.Bundle
import com.github.rougsig.rxflux.android.core.*
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
    store.addModules(screenScope, *scopedConfig.screenModules)
  }

  override fun onDestroy() {
    super.onDestroy()

    val scopedConfig = config as Config<VS>
    val store = screenScope.instance<Store>()
    store.removeModules(screenScope, *scopedConfig.screenModules)

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
