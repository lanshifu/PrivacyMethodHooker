package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.saveResult


@Keep
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object ClipboardManagerHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = ClipboardManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getPrimaryClip(
        manager: ClipboardManager,
        callerClassName: String
    ): ClipData? {
        val key = "ClipboardManager#getPrimaryClip"
        val checkResult = checkCacheAndPrivacy<ClipData>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return saveResult(key, manager.primaryClip, callerClassName)
    }

}
