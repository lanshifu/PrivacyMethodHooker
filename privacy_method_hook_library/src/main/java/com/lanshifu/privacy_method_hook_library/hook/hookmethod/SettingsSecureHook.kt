package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.Settings
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.saveResult


@Keep
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object SettingsSecureHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = Settings.Secure::class,
        oriAccess = AsmMethodOpcodes.INVOKESTATIC
    )
    fun getString(
        resolver: ContentResolver,
        name: String,
        callerClassName: String
    ): String? {
        val key = "Settings\$Secure#getString(${name})"
        when (name) {
            "android_id" -> {
            }
            // 6.0 之后获取不到bluetooth_address了
            "bluetooth_address" -> {
            }
            "bluetooth_name" -> {
            }
            else -> {
                return Settings.Secure.getString(resolver, key)
            }
        }

        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: ""
        }
        return saveResult(name, Settings.Secure.getString(resolver, name) ?: "", callerClassName)
    }


}
