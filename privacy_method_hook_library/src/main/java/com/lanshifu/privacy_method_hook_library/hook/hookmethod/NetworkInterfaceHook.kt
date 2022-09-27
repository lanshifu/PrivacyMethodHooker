package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.hardware.SensorManager
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.saveResult
import java.net.NetworkInterface


@Keep
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object NetworkInterfaceHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = NetworkInterface::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getHardwareAddress(
        networkInterface: NetworkInterface,
        callerClassName: String
    ): ByteArray? {
        val key = "NetworkInterface#getHardwareAddress"
        val checkResult = checkCacheAndPrivacy<ByteArray>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return try {
            saveResult(key, networkInterface.hardwareAddress, callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
