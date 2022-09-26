package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.saveResult


@Keep
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object BlueToothAdapterHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = BluetoothAdapter::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getAddress(
        bluetoothAdapter: BluetoothAdapter,
        callerClassName: String
    ): String? {
        val key = "BluetoothAdapter#getAddress"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return try {
            saveResult(key, bluetoothAdapter.address ?: "", callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = BluetoothAdapter::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getName(
        bluetoothAdapter: BluetoothAdapter,
        callerClassName: String
    ): String? {
        val key = "BluetoothAdapter#getName"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return try {
            saveResult(key, bluetoothAdapter.name, callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}
