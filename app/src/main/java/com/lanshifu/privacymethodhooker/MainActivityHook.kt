package com.lanshifu.privacymethodhooker

import android.annotation.SuppressLint
import android.util.Log
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace

/**
 * @author lanxiaobin
 * @date 2022/9/29
 */
@SuppressLint("NewApi")
object MainActivityHook {

    /**
     * 统计某个public方法耗时，例如 MainActivity#updateData()
     */
    @JvmStatic
    @AsmMethodReplace(
        oriClass = MainActivity::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun updateData(
        activity: MainActivity,
        callerClassName: String
    ) {
        val startTime = System.currentTimeMillis()

        activity.updateData()

        val costTime = System.currentTimeMillis() - startTime
        Log.i(
            "MainActivityHook",
            "MainActivity updateData cost $costTime ms,callerClassName=$callerClassName"
        )
    }
}