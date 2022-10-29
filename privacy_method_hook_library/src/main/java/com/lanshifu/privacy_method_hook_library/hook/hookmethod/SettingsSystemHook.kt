package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.Settings
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.savePrivacyMethodResult

@Keep
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object SettingsSystemHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = Settings.System::class,
        oriAccess = AsmMethodOpcodes.INVOKESTATIC
    )
    fun getString(
        resolver: ContentResolver,
        name: String,
        callerClassName: String
    ): String? {

        val key = "Settings\$System#getString(${name})"
        when (name) {
            "android_id" -> {
            }
            else -> {
                return Settings.System.getString(resolver, name)
            }
        }

        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: ""
        }
        return savePrivacyMethodResult(key, Settings.System.getString(resolver, name) ?: "", callerClassName)
    }


}
