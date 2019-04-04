package com.github.rougsig.rxflux.android

import android.content.Context
import com.github.rougsig.rxflux.android.core.AppSchedulers
import com.github.rougsig.rxflux.android.ui.core.DefaultAppSchedulers
import toothpick.config.Module

internal class AppModule(application: FluxApplication) : Module() {
  init {
    bind(Context::class.java).toInstance(application)
    bind(FluxApplication::class.java).toInstance(application)
    bind(AppSchedulers::class.java).toInstance(DefaultAppSchedulers)
  }
}
