package com.github.rougsig.rxflux.android.core

import com.github.rougsig.rxflux.core.actor.Actor
import com.github.rougsig.rxflux.core.reducer.Reducer
import com.github.rougsig.rxflux.core.store.Store
import toothpick.Scope
import toothpick.config.Module

fun Store.addModule(scope: Scope, module: Module) {
  module.bindingSet.forEach { binding ->
    when (val instance = scope.getInstance(binding.key)) {
      is Reducer<*> -> addReducer(instance)
      is Actor -> addActor(instance)
    }
  }
}

fun Store.removeModule(scope: Scope, module: Module) {
  module.bindingSet.forEach { binding ->
    when (val instance = scope.getInstance(binding.key)) {
      is Reducer<*> -> removeReducer(instance)
      is Actor -> removeActor(instance)
    }
  }
}

fun Store.addModules(scope: Scope, vararg modules: Module) {
  modules.forEach { module ->
    addModule(scope, module)
  }
}

fun Store.removeModules(scope: Scope, vararg modules: Module) {
  modules.forEach { module ->
    removeModule(scope, module)
  }
}
