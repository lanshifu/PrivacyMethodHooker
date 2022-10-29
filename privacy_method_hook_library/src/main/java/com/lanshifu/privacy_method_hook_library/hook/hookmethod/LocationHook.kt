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
object LocationHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = LocationManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getLatitude(
        manager: Location,
        callerClassName: String
    ): Double {
        val key = "Location#getLatitude"
        val checkResult = checkCacheAndPrivacy<Double>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: 0.0
        }
        return savePrivacyMethodResult(key, manager.latitude, callerClassName)
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = LocationManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getLongitude(
        manager: Location,
        callerClassName: String
    ): Double {
        val key = "Location#getLongitude"
        val checkResult = checkCacheAndPrivacy<Double>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: 0.0
        }
        return savePrivacyMethodResult(key, manager.longitude, callerClassName)
    }

}
