package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.Settings
import android.telephony.CellLocation
import android.telephony.NeighboringCellInfo
import android.telephony.TelephonyManager
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.saveResult

@Keep
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object `Settings$SystemHook` {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = Settings.System::class,
        oriAccess = AsmMethodOpcodes.INVOKESTATIC
    )
    fun getString(
        resolver: ContentResolver,
        name: String,
        callerClassName: String
    ): String {

        var key = name
        when (name) {
            "android_id" -> {
                key = "${key}_${Settings.Secure.ANDROID_ID}"
            }
            else -> {
                return Settings.System.getString(resolver, name)
            }
        }

        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: ""
        }
        return saveResult(key, Settings.System.getString(resolver, name) ?: "", callerClassName)
    }


}
