package com.github.rougsig.rxflux.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.github.rougsig.rxflux.android.ui.todolist.createTaskListController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private lateinit var router: Router

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    router = Conductor.attachRouter(this, main_container, savedInstanceState)
    if (!router.hasRootController())
      router.setRoot(RouterTransaction.with(createTaskListController()))
  }

  override fun onBackPressed() {
    if (!router.handleBack())
      super.onBackPressed()
  }
}
