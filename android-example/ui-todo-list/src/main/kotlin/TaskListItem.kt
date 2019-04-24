package com.github.rougsig.rxflux.android.ui.todolist

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import kotlinx.android.synthetic.main.task_list_item.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
internal class TaskListItem @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  init {
    View.inflate(context, R.layout.task_list_item, this)
  }

  @ModelProp
  fun setText(text: String) {
    task_list_item_title.text = text
  }

  @CallbackProp
  fun setOnClick(listener: (() -> Unit)?) {
    if (listener != null) task_list_item.setOnClickListener { listener.invoke() }
  }
}

