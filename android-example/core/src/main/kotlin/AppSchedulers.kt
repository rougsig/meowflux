package com.github.rougsig.rxflux.android.core

import io.reactivex.Scheduler

interface AppSchedulers {
  val io: Scheduler
  val computation: Scheduler
  val ui: Scheduler
}
