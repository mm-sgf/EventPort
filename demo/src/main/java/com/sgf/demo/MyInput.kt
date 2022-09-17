package com.sgf.demo

import com.sgf.eventrouter.bean.Input

class MyInput : Input {
    override fun getMsg()  : String{
        return "this is My Input class"
    }
}