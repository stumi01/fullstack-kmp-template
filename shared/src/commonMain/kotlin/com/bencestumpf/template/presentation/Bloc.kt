package com.bencestumpf.template.presentation

import kotlinx.coroutines.flow.SharedFlow

interface Bloc<State : Any, Action : Any> {
    fun dispatch(action: Action)

    fun subscribe(): SharedFlow<State>

}
