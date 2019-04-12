package com.github.rougsig.rxflux2.core

import io.reactivex.Observable

typealias Action<S, M, G, A> = (context: RxFlux2.Context<S, M, G, A>) -> Observable<Unit>
