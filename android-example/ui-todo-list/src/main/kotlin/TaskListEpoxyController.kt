package com.github.rougsig.rxflux.android.ui.todolist

import com.airbnb.epoxy.TypedEpoxyController
import com.github.rougsig.rxflux.android.enitity.TodoItem
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

private sealed class Event
private data class ItemClick(val itemId: Long) : Event()

internal class TaskListEpoxyController : TypedEpoxyController<List<TodoItem>>() {
  private val eventsRelay = PublishRelay.create<Event>()

  override fun buildModels(data: List<TodoItem>) {
    data.forEach {
      taskListItem {
        id(it.id)
        text(it.text)
        onClick { eventsRelay.accept(ItemClick(it.id)) }
      }
    }
  }

  fun itemClicks(): Observable<Long> {
    return eventsRelay
      .ofType(ItemClick::class.java)
      .map(ItemClick::itemId)
  }
}
