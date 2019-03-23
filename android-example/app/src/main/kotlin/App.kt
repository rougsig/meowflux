package com.github.rougsig.rxflux.android

import android.app.Application
import android.os.Looper
import com.jakewharton.threetenabp.AndroidThreeTen
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)
    configureRxAndroid()
    configureLogging()
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
