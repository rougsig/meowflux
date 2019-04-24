package com.github.rougsig.rxflux.android.ui.core.routing

import com.github.rougsig.rxflux.core.action.Action
import com.github.rougsig.rxflux.core.actor.Actor

interface RouterActor : Actor {
  fun showCreateTodoItem(): Action
  
  fun showTodoItemDetails(id: Long): Action
}
