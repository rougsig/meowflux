package com.github.rougsig.meowflux.android

import android.content.Context
import android.view.View
import com.github.rougsig.meowflux.core.Action
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <V : View, S : Any, SP : Any, DP : Any> connect(
  viewConstructor: (Context, Flow<SP>, DP) -> V,
  mapStateToProps: (S, Context) -> SP,
  mapDispatchToProps: ((Action) -> Unit) -> DP
): (Context) -> V {
  return { context ->
    val store = context.store<S>()
    viewConstructor(
      context,
      store.stateFlow.map { mapStateToProps(it, context) },
      mapDispatchToProps { store.dispatch(it) }
    )
  }
}

fun <V : View, S : Any, SP : Any, DP : Any, OP : Any> connect(
  viewConstructor: (Context, Flow<SP>, DP, OP) -> V,
  mapStateToProps: (S, Context) -> SP,
  mapDispatchToProps: ((Action) -> Unit) -> DP
): (Context, ownProps: OP) -> V {
  return { context, ownProps ->
    val store = context.store<S>()
    viewConstructor(
      context,
      store.stateFlow.map { mapStateToProps(it, context) },
      mapDispatchToProps { store.dispatch(it) },
      ownProps
    )
  }
}
