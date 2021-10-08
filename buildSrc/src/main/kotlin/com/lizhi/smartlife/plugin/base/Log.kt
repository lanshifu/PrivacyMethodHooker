package com.lizhi.smartlife.plugin.base


object Log {
    @JvmStatic
    fun info(msg: Any) {
        try {
            println((String.format("{%s}", msg.toString())))
            //todo 输出log到build目录下
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}