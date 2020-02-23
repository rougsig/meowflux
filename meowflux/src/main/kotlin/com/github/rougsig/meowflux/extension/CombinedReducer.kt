package com.github.rougsig.meowflux.extension

import com.github.rougsig.meowflux.core.Action
import com.github.rougsig.meowflux.core.Reducer

inline fun <reified A : Action, S : Any> CombinedReducer(
        initialState: S,
        reducers: Iterable<Reducer<S>>
): Reducer<S> = { action, previousState ->
    reducers.fold(previousState ?: initialState) { previousState, reducer ->
        reducer(action, previousState)
    }
}