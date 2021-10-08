package com.lizhi.smartlife.plugin.base

interface AsmHelper {
    fun modifyClass(srcClass: ByteArray?): ByteArray?
}