package com.github.rougsig.rxflux.android.ui.core.routing

import com.bluelinelabs.conductor.Router
import com.github.rougsig.rxflux.core.actor.ConcatMapActorTaskComposer
import com.github.rougsig.rxflux.dsl.ConfigurableActor
import javax.inject.Inject

private object ShowCreateScreen
private data class ShowDetailsScreen(val id: Long)

internal class RouterActorImpl @Inject constructor(
  private val router: Router
) :
  ConfigurableActor(ConcatMapActorTaskComposer()),
  RouterActor {

  init {
    task(ShowCreateScreen::class) {
    }

    task(ShowDetailsScreen::class) {
    }
  }

  override fun showCreateTodoItem() = createAction { ShowCreateScreen }

  override fun showTodoItemDetails(id: Long) = createAction { ShowDetailsScreen(id) }
}
