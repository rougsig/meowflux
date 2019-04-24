package com.github.rougsig.rxflux.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.github.rougsig.rxflux.android.core.APP_SCOPE_NAME
import com.github.rougsig.rxflux.android.core.addModules
import com.github.rougsig.rxflux.android.core.instance
import com.github.rougsig.rxflux.android.ui.todolist.createTaskListController
import com.github.rougsig.rxflux.core.store.Store
import kotlinx.android.synthetic.main.activity_main.*
import toothpick.Toothpick

class MainActivity : AppCompatActivity() {

  private lateinit var router: Router

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    router = Conductor.attachRouter(this, main_container, savedInstanceState)

    val appScope = Toothpick.openScope(APP_SCOPE_NAME)
    val activityModule = ActivityModule(router)
    appScope.installModules(activityModule)
    appScope.instance<Store>().addModules(appScope, activityModule)

    if (!router.hasRootController())
      router.setRoot(RouterTransaction.with(createTaskListController()))
  }

  override fun onBackPressed() {
    if (!router.handleBack())
      super.onBackPressed()
  }
}
