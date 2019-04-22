package com.github.rougsig.rxflux.android.domain

import toothpick.config.Module

class StoreModule : Module() {
  init {
    StoreBindings.bindInto(this)
  }
}

