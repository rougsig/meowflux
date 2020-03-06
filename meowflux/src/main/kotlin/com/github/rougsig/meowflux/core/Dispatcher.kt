package com.github.rougsig.meowflux.core

typealias Dispatcher = (action: Action) -> Unit
typealias SuspendDispatcher = suspend (action: Action) -> Unit
