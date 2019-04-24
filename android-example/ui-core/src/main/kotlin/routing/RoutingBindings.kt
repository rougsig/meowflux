package com.github.rougsig.rxflux.android.ui.core.routing

import com.github.rougsig.rxflux.android.core.ToothpickModuleBindings
import toothpick.config.Module

object RoutingBindings : ToothpickModuleBindings {
  override fun bindInto(module: Module) {
    module
      .bind(RouterActor::class.java)
      .to(RouterActorImpl::class.java)
      .singletonInScope()
  }
}
