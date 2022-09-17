package com.sgf.demo.event

import com.sgf.eventport.annotation.MultiEvent

@MultiEvent
interface AppMultiEvent {
    fun putAppMessage(msg: String)
}