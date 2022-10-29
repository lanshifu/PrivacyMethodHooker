package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.savePrivacyMethodResult


@Keep
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object SensorManagerHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = SensorManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getSensorList(
        sensorManager: SensorManager,
        type: Int,
        callerClassName: String
    ): List<Sensor> {
        val key = "SensorManager#getSensorList"
        val checkResult = checkCacheAndPrivacy<List<Sensor>>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: emptyList()
        }
        return savePrivacyMethodResult(key, sensorManager.getSensorList(type) ?: emptyList(), callerClassName)
    }

}
