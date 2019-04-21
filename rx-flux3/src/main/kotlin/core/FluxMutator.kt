package com.github.rougsig.rxflux3.core

typealias FluxMutator<S, A> = (state: S, action: A) -> S
