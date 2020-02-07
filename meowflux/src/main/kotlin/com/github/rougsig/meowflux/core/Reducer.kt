package com.github.rougsig.meowflux.core

typealias Reducer<S> = (action: Action, previousState: S?) -> S
