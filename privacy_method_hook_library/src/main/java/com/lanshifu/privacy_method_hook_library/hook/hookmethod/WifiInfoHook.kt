package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.net.wifi.WifiInfo
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.saveResult


@Keep
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object WifiInfoHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = WifiInfo::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getMacAddress(
        wifiInfo: WifiInfo,
        callerClassName: String
    ): String? {
        val key = "WifiInfo#getMacAddress"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: ""
        }
        return try {
            saveResult(key, wifiInfo.macAddress ?: "", callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = WifiInfo::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getIpAddress(
        wifiInfo: WifiInfo,
        callerClassName: String
    ): Int? {
        val key = "WifiInfo#getIpAddress"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return -1
        }
        return try {
            saveResult(key, wifiInfo.ipAddress, callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = WifiInfo::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getSSID(
        wifiInfo: WifiInfo,
        callerClassName: String
    ): String? {
        val key = "WifiInfo#getSSID"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: ""
        }
        return try {
            saveResult(key, wifiInfo.ssid ?: "", callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = WifiInfo::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getBSSID(
        wifiInfo: WifiInfo,
        callerClassName: String
    ): String? {
        val key = "WifiInfo#getBSSID"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: ""
        }
        return try {
            saveResult(key, wifiInfo.bssid ?: "", callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }


}
