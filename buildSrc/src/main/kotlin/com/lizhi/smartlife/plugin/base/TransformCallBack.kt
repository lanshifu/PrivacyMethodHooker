package com.lizhi.smartlife.plugin.base

interface TransformCallBack {
    fun process(className: String, classBytes: ByteArray?): ByteArray?
}