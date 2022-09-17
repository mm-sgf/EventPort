package com.sgf.moduleone

import android.util.Log
import android.widget.Toast
import com.sgf.eventport.EventPort
import com.sgf.eventport.annotation.ReceiveEvent
import com.sgf.eventrouter.AppContextEvent
import com.sgf.eventrouter.module.ModuleMultiEvent
import com.sgf.eventrouter.module.ModuleSingleEvent

/**
 * 1.设置接收注释
 * 2.实现事件接口
 */
@ReceiveEvent
class ModuleReceive : ModuleSingleEvent, ModuleMultiEvent {
    companion object {
        private const val TAG = "ModuleReceive"
    }

    private val message : ModuleMessage = ModuleMessage()

    init {
        // 注入接收事件实体
        EventPort.inject(this)
    }

    override fun getMessage(): String {
        return "hello world"
    }

    override fun printModuleMessage(msg: String) {
        Log.d(TAG, "printModuleMessage: $msg")
    }
}