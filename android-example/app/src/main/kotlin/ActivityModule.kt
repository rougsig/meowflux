package com.github.rougsig.rxflux.android

import com.bluelinelabs.conductor.Router
import com.github.rougsig.rxflux.android.ui.core.routing.RoutingBindings
import toothpick.config.Module

internal class ActivityModule(router: Router) : Module() {
  init {
    RoutingBindings.bindInto(this)

    bind(Router::class.java).toInstance(router)
  }
}
