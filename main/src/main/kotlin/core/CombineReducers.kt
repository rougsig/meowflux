package com.github.rougsig.rxflux.core

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.immutableMapOf

class ReducerField<T, S : Any, A : Action>(
  val field: FluxState.Field<T, S>,
  val reducer: Reducer<T, A>
) {
  operator fun component1() = field
  operator fun component2() = reducer
}

infix fun <T, S : Any, A : Action> FluxState.Field<T, S>.reduce(
  reducer: Reducer<T, A>
): ReducerField<T, S, A> {
  return ReducerField(this, reducer)
}

inline fun <reified S : FluxState<S>, A : Action> combineReducers(
  vararg reducers: ReducerField<*, S, A>
): Reducer<S, A> {
  return { s: S?, action: A ->
    val state = s ?: S::class.java.constructors
      .find { it.parameterCount == 1 && it.parameterTypes.contains(ImmutableMap::class.java) }!!
      .newInstance(immutableMapOf<Any, Any?>()) as S

    reducers.fold(state) { acc, it ->
      val (field, reducer) = it as ReducerField<Any?, S, A>

      val oldValue: FluxState<Any?>? = acc.getField(field) as FluxState<Any?>?
      val newValue: Any? = reducer.invoke(oldValue, action)

      if (oldValue === newValue) {
        acc
      } else {
        acc.setField(field, newValue)
      }
    }
  }
}
