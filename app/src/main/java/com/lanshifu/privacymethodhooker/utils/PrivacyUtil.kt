package com.lanshifu.privacymethodhooker.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.ContentResolver
import android.hardware.Sensor
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.DhcpInfo
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.CellInfo
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.Keep
import com.lanshifu.privacy_method_annotation.AsmClass
import com.lanshifu.privacy_method_annotation.AsmField
import com.lanshifu.privacy_method_annotation.AsmMethodOpcodes
import java.util.*
import kotlin.collections.HashMap

/**
 * @author lanxiaobin
 * @date 2021/10/9
 *
 * 1、不要被混淆
 *
 * 2、Kotlin 的方法必须要使用JvmStatic注解，否则Java调用会报错
 *
 *     java.lang.IncompatibleClassChangeError: The method
 *     'java.lang.String com.lanshifu.privacymethodhooker.utils.PrivacyUtil.getString(android.content.ContentResolver, java.lang.String)'
 *     was expected to be of type static but instead was found to be of type virtual
 *     (declaration of 'com.lanshifu.privacymethodhooker.MainActivity' appears in /data/app/com.lanshifu.privacymethodhooker-2/base.apk)
 */
@Keep
@AsmClass
object PrivacyUtil {

    private val TAG = "PrivacyUtil"

    var isAgreePrivacy = false

    private var stringCache = HashMap<String, String>()

    private fun checkAgreePrivacy(name: String): Boolean {

        //todo cache

        if (!isAgreePrivacy) {
            logW("$name: isAgreePrivacy=false")
            //打印堆栈
            return false
        }

        return true
    }

    private fun putStringCache(key: String, value: String?) {
        value?.let {
            stringCache.put(key, value)
        }
    }

    @JvmStatic
    @AsmField(oriClass = ActivityManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getRunningAppProcesses(manager: ActivityManager): List<RunningAppProcessInfo?> {

        if (!checkAgreePrivacy("getRunningAppProcesses")) {
            return emptyList()
        }
        return manager.runningAppProcesses
    }

    @JvmStatic
    @AsmField(oriClass = ActivityManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getRecentTasks(
        manager: ActivityManager,
        maxNum: Int,
        flags: Int
    ): List<ActivityManager.RecentTaskInfo>? {
        if (!checkAgreePrivacy("getRecentTasks")) {
            return emptyList()
        }

        return manager.getRecentTasks(maxNum, flags)

    }

    @JvmStatic
    @AsmField(oriClass = ActivityManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getRunningTasks(
        manager: ActivityManager,
        maxNum: Int
    ): List<ActivityManager.RunningTaskInfo>? {
        if (!checkAgreePrivacy("getRunningTasks")) {
            return emptyList()
        }
        return manager.getRunningTasks(maxNum)

    }

    /**
     * 读取基站信息
     */
    @JvmStatic
    @SuppressLint("MissingPermission")
    @AsmField(oriClass = TelephonyManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getAllCellInfo(manager: TelephonyManager): List<CellInfo>? {
        if (!checkAgreePrivacy("getAllCellInfo")) {
            return emptyList()
        }
        return  manager.getAllCellInfo()
    }

    /**
     * 读取基站信息
     */
    @SuppressLint("HardwareIds")
    @JvmStatic
    @AsmField(oriClass = TelephonyManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getDeviceId(manager: TelephonyManager): String? {
        if (!checkAgreePrivacy("getDeviceId")) {
            return ""
        }
        return  manager.deviceId

    }

    /**
     * 读取基站信息
     */
    @SuppressLint("HardwareIds")
    @JvmStatic
    @AsmField(oriClass = TelephonyManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getImei(manager: TelephonyManager): String? {
        if (!checkAgreePrivacy("getImei")) {
            return ""
        }
        return  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.getImei()
        } else {
            //"(api 要大于android O)"
            ""
        }
    }

    /**
     * 读取ICCID
     */
    @SuppressLint("HardwareIds")
    @JvmStatic
    @AsmField(oriClass = TelephonyManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getSimSerialNumber(manager: TelephonyManager): String? {
        if (!checkAgreePrivacy("getSimSerialNumber")) {
            return ""
        }
        //不允许App读取，拦截调用
        return ""
    }

    /**
     * 读取WIFI的SSID
     */
    @JvmStatic
    @AsmField(oriClass = WifiInfo::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getSSID(manager: WifiInfo): String? {
        if (!checkAgreePrivacy("getSSID")) {
            return ""
        }
        return manager.ssid
    }

    /**
     * 读取WIFI的SSID
     */
    @JvmStatic
    @AsmField(oriClass = WifiInfo::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getBSSID(manager: WifiInfo): String? {
        if (!checkAgreePrivacy("getBSSID")) {
            return ""
        }
        return manager.bssid
    }

    /**
     * 读取WIFI的SSID
     */
    @SuppressLint("HardwareIds")
    @JvmStatic
    @AsmField(oriClass = WifiInfo::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getMacAddress(manager: WifiInfo): String? {
        if (!checkAgreePrivacy("getMacAddress")) {
            return ""
        }
        return manager.macAddress
    }

    /**
     * 读取AndroidId
     */
    @JvmStatic
    @AsmField(oriClass = Settings.System::class, oriAccess = AsmMethodOpcodes.INVOKESTATIC)
    fun getString(resolver: ContentResolver, name: String): String? {
        //处理AndroidId
        if (Settings.Secure.ANDROID_ID == name) {

            if (!checkAgreePrivacy("ANDROID_ID")) {
                return ""
            }
            return Settings.System.getString(resolver, name)
        }

        return Settings.System.getString(resolver, name)
    }

    /**
     * getSensorList
     */
    @JvmStatic
    @AsmField(oriClass = SensorManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getSensorList(manager: SensorManager, type: Int): MutableList<Sensor>? {
        if (!checkAgreePrivacy("getSensorList")) {
            return mutableListOf()
        }
        return manager.getSensorList(type)

    }

    /**
     * 读取WIFI扫描结果
     */
    @JvmStatic
    @AsmField(oriClass = WifiManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getScanResults(manager: WifiManager): MutableList<ScanResult>? {
        if (!checkAgreePrivacy("getScanResults")) {
            return mutableListOf()
        }
        return manager.getScanResults()

    }

    /**
     * 读取DHCP信息
     */
    @JvmStatic
    @AsmField(oriClass = WifiManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getDhcpInfo(manager: WifiManager): DhcpInfo? {
        if (!checkAgreePrivacy("getDhcpInfo")) {
            return null
        }
        return manager.getDhcpInfo()

    }


    /**
     * 读取DHCP信息
     */
    @SuppressLint("MissingPermission")
    @JvmStatic
    @AsmField(oriClass = WifiManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getConfiguredNetworks(manager: WifiManager): MutableList<WifiConfiguration>? {
        if (!checkAgreePrivacy("getConfiguredNetworks")) {
            return mutableListOf()
        }
        return manager.getConfiguredNetworks()

    }


    /**
     * 读取位置信息
     */
    @JvmStatic
    @SuppressLint("MissingPermission")
    @AsmField(oriClass = LocationManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getLastKnownLocation(
        manager: LocationManager, provider: String
    ): Location? {
        if (!checkAgreePrivacy("getLastKnownLocation")) {
            return null
        }
        return manager.getLastKnownLocation(provider)
    }


    /**
     * 读取位置信息
     */
//    @JvmStatic
//    @AsmField(oriClass = LocationManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
//    fun getLastLocation(
//        manager: LocationManager, provider: String
//    ): Location? {
//        log("getLastKnownLocation: isAgreePrivacy=$isAgreePrivacy")
//        if (isAgreePrivacy) {
//            return manager.getLastLocation(provider)
//        }
//        return null
//    }


    /**
     * 监视精细行动轨迹
     */
    @SuppressLint("MissingPermission")
    @JvmStatic
    @AsmField(oriClass = LocationManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun requestLocationUpdates(
        manager: LocationManager, provider: String, minTime: Long, minDistance: Float,
        listener: LocationListener
    ) {
        if (!checkAgreePrivacy("requestLocationUpdates")) {
            return
        }
        manager.requestLocationUpdates(provider, minTime, minDistance, listener)

    }


    private fun log(log: String) {
        Log.i(TAG, log)
    }
    private fun logW(log: String) {
        Log.w(TAG, log)
    }

}