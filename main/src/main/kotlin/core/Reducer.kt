package com.github.rougsig.rxflux.core

typealias Reducer<S, A> = (state: S?, action: A) -> S
