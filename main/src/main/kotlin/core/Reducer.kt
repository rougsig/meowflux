package com.github.rougsig.rxflux.core

typealias Reducer<S, A> = (state: S, action: A) -> S

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

fun <S : FluxState<S>, A : Action> combineReducers(
    vararg reducers: ReducerField<*, S, A>
): Reducer<S, A> {
  return { state: S, action: A ->
    reducers.fold(state) { acc, it ->
      val (field, reducer) = it as ReducerField<Any?, S, A>

      val oldValue: Any? = acc.getField(field)
      val newValue: Any? = reducer.invoke(oldValue, action)

      if (oldValue === newValue) {
        acc
      } else {
        acc.setField(field, newValue)
      }
    }
  }
}
