package com.github.rougsig.rxflux.android

import android.app.Application
import android.os.Looper
import com.github.rougsig.rxflux.android.core.APP_SCOPE_NAME
import com.github.rougsig.rxflux.android.domain.di.StoreModule
import com.jakewharton.threetenabp.AndroidThreeTen
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import toothpick.Toothpick

class FluxApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)
    configureRxAndroid()
    configureLogging()

    val appScope = Toothpick.openScope(APP_SCOPE_NAME)
    appScope.installModules(StoreModule())
  }

  private fun configureRxAndroid() {
    RxAndroidPlugins.setInitMainThreadSchedulerHandler {
      AndroidSchedulers.from(Looper.getMainLooper(), true)
    }
  }

  private fun configureLogging() {
    Timber.plant(Timber.DebugTree())
  }
}
