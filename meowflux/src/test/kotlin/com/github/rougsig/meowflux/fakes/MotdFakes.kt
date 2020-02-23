package com.github.rougsig.meowflux.fakes

import com.github.rougsig.meowflux.core.Action

sealed class MotdAction: Action {
    object MotdAddFirstWordAction: MotdAction()
    object MotdAddSecondWordAction: MotdAction()
    object MotdAddThirdWordAction: MotdAction()
    object MotdAddForthWordAction: MotdAction()
    object MotdAddBangSignAction: MotdAction()
}
