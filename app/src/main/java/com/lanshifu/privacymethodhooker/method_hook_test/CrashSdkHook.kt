package com.lanshifu.privacymethodhooker.method_hook_test

import android.annotation.SuppressLint
import android.util.Log
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.log.LogUtil

/**
 * @author lanxiaobin
 * @date 2022/9/29
 */
object CrashSdkHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = CrashSdk::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun crash(
        crashSdk: CrashSdk,
        callerClassName: String
    ) {
        try {
            crashSdk.crash()
        } catch (e: Exception) {
            Log.e("CrashSdkHook", "hook,e=${e.message}")
            e.printStackTrace()
        }
    }
}