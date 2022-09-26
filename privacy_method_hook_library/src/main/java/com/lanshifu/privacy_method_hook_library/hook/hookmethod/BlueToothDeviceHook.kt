package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.saveResult


@Keep
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object BlueToothDeviceHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = BluetoothDevice::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getAddress(
        bluetoothDevice: BluetoothDevice,
        callerClassName: String
    ): String? {
        val key = "BluetoothDevice#getAddress"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return try {
            saveResult(key, bluetoothDevice.address ?: "", callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = BluetoothDevice::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getName(
        bluetoothDevice: BluetoothDevice,
        callerClassName: String
    ): String? {
        val key = "BluetoothDevice#getName"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return try {
            saveResult(key, bluetoothDevice.name, callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}
