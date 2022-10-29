package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.hardware.Sensor
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.savePrivacyMethodResult


@Keep
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object SensorHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = Sensor::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getName(
        sensor: Sensor,
        callerClassName: String
    ): String? {
        val key = "Sensor#getName"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return savePrivacyMethodResult(key, sensor.name, callerClassName)
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = Sensor::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getType(
        sensor: Sensor,
        callerClassName: String
    ): Int {
        val key = "Sensor#getType"
        val checkResult = checkCacheAndPrivacy<Int>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: -1
        }
        return savePrivacyMethodResult(key, sensor.type, callerClassName)
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = Sensor::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getVersion(
        sensor: Sensor,
        callerClassName: String
    ): Int {
        val key = "Sensor#getVersion"
        val checkResult = checkCacheAndPrivacy<Int>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: -1
        }
        return savePrivacyMethodResult(key, sensor.version, callerClassName)
    }

}
