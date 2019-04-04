package com.github.rougsig.rxflux.android.ui.core

import com.github.rougsig.rxflux.android.core.AppSchedulers
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DefaultAppSchedulers : AppSchedulers {
  override val io: Scheduler get() = Schedulers.io()
  override val computation: Scheduler get() = Schedulers.computation()
  override val ui: Scheduler get() = AndroidSchedulers.mainThread()
}
