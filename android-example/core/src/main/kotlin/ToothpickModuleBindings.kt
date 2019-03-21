package com.github.rougsig.rxflux.android.core

import com.github.rougsig.rxflux.android.enitity.TodoItem
import io.reactivex.Completable
import io.reactivex.Observable
import toothpick.config.Module

interface ToothpickModuleBindings {
  fun bindInto(module: Module)
}

