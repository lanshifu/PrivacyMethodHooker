package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationManager
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.savePrivacyMethodResult


@Keep
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object LocationManagerHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = LocationManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getLastKnownLocation(
        manager: LocationManager,
        provider: String,
        callerClassName: String
    ): Location? {
        val key = "LocationManager#getLastKnownLocation"
        val checkResult = checkCacheAndPrivacy<Location>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return try {
            savePrivacyMethodResult(key, manager.getLastKnownLocation(provider), callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
