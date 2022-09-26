package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.net.DhcpInfo
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.saveResult


@Keep
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object WifiManagerHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = WifiManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getScanResults(
        wifiManager: WifiManager,
        callerClassName: String
    ): List<ScanResult> {
        val key = "WifiInfo#getScanResults"
        val checkResult = checkCacheAndPrivacy<List<ScanResult>>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData ?: emptyList()
        }
        return try {
            saveResult(key, wifiManager.scanResults ?: emptyList(), callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = WifiManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getDhcpInfo(
        wifiManager: WifiManager,
        callerClassName: String
    ): DhcpInfo? {
        val key = "WifiInfo#getDhcpInfo"
        val checkResult = checkCacheAndPrivacy<DhcpInfo>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return try {
            saveResult(key, wifiManager.dhcpInfo, callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = WifiManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getConnectionInfo(
        wifiManager: WifiManager,
        callerClassName: String
    ): WifiInfo? {
        val key = "WifiInfo#getConnectionInfo"
        val checkResult = checkCacheAndPrivacy<WifiInfo>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return try {
            saveResult(key, wifiManager.connectionInfo, callerClassName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
