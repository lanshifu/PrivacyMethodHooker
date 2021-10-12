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

    private fun putStringCache(key: String, value: String?) {
        value?.let {
//            stringCache.put(key, value)
        }
    }

    @JvmStatic
    @AsmField(oriClass = ActivityManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getRunningAppProcesses(manager: ActivityManager): List<RunningAppProcessInfo?> {

        log("getRunningAppProcesses: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy) {
            return manager.runningAppProcesses
        }

        return emptyList()
    }

    @JvmStatic
    @AsmField(oriClass = ActivityManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getRecentTasks(
        manager: ActivityManager,
        maxNum: Int,
        flags: Int
    ): List<ActivityManager.RecentTaskInfo>? {

        log("getRecentTasks: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy) {
            return manager.getRecentTasks(maxNum, flags)
        }

        return emptyList()
    }

    @JvmStatic
    @AsmField(oriClass = ActivityManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getRunningTasks(
        manager: ActivityManager,
        maxNum: Int
    ): List<ActivityManager.RunningTaskInfo>? {

        log("getRecentTasks: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy) {
            return manager.getRunningTasks(maxNum)
        }

        return emptyList()
    }

    /**
     * 读取基站信息
     */
    @JvmStatic
    @SuppressLint("MissingPermission")
    @AsmField(oriClass = TelephonyManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getAllCellInfo(manager: TelephonyManager): List<CellInfo>? {
        log("getAllCellInfo: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy) {
            return manager.getAllCellInfo()
        }

        return emptyList()
    }

    /**
     * 读取基站信息
     */
    @SuppressLint("HardwareIds")
    @JvmStatic
    @AsmField(oriClass = TelephonyManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getDeviceId(manager: TelephonyManager): String? {
        log("getDeviceId: isAgreePrivacy=$isAgreePrivacy")
        val key = "deviceId"
        if (stringCache[key] != null) {
            return stringCache[key]
        }
        if (isAgreePrivacy) {
            val value = manager.deviceId
            putStringCache(key, value)
            return value
        }
        return ""
    }

    /**
     * 读取基站信息
     */
    @SuppressLint("HardwareIds")
    @JvmStatic
    @AsmField(oriClass = TelephonyManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getImei(manager: TelephonyManager): String? {
        log("getImei: isAgreePrivacy=$isAgreePrivacy")
        val key = "getImei"
        if (stringCache[key] != null) {
            return stringCache[key]
        }
        if (isAgreePrivacy) {
            val value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.getImei()
            } else {
                "(api 要大于android O)"
            }
            putStringCache(key, value)
            return value
        }
        return ""
    }

    /**
     * 读取ICCID
     */
    @SuppressLint("HardwareIds")
    @JvmStatic
    @AsmField(oriClass = TelephonyManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getSimSerialNumber(manager: TelephonyManager): String? {
        log("getSimSerialNumber: isAgreePrivacy=$isAgreePrivacy")
        //不允许App读取，拦截调用
        return ""
    }

    /**
     * 读取WIFI的SSID
     */
    @JvmStatic
    @AsmField(oriClass = WifiInfo::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getSSID(manager: WifiInfo): String? {
        log("getSSID: isAgreePrivacy=$isAgreePrivacy")
        val key = "ssid"
        if (stringCache[key] != null) {
            return stringCache[key]
        }
        if (isAgreePrivacy) {
            val value = manager.ssid
            putStringCache(key, value)
            return value
        }
        return null
    }

    /**
     * 读取WIFI的SSID
     */
    @JvmStatic
    @AsmField(oriClass = WifiInfo::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getBSSID(manager: WifiInfo): String? {
        log("getBSSID: isAgreePrivacy=$isAgreePrivacy")
        val key = "bssid"
        if (stringCache[key] != null) {
            return stringCache[key]
        }
        if (isAgreePrivacy) {
            val value = manager.bssid
            putStringCache(key, value)
            return value
        }

        return null
    }

    /**
     * 读取WIFI的SSID
     */
    @SuppressLint("HardwareIds")
    @JvmStatic
    @AsmField(oriClass = WifiInfo::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getMacAddress(manager: WifiInfo): String? {
        log("getBSSID: isAgreePrivacy=$isAgreePrivacy")
        if (stringCache["macAddress"] != null) {
            return stringCache["macAddress"]
        }
        if (isAgreePrivacy) {
            val macAddress = manager.macAddress
            putStringCache("macAddress", macAddress)
            return macAddress
        }
        return null
    }

    /**
     * 读取AndroidId
     */
    @JvmStatic
    @AsmField(oriClass = Settings.System::class, oriAccess = AsmMethodOpcodes.INVOKESTATIC)
    fun getString(resolver: ContentResolver, name: String): String? {
        log("Settings.System.getString,name=$name,isAgreePrivacy=$isAgreePrivacy")


        //处理AndroidId
        if (Settings.Secure.ANDROID_ID == name) {
            if (stringCache[Settings.Secure.ANDROID_ID] != null) {
                log("Settings.System.getString cacheAndroidId=${stringCache[Settings.Secure.ANDROID_ID]}")
                return stringCache[Settings.Secure.ANDROID_ID]
            }
            if (isAgreePrivacy) {
                val androidId = Settings.System.getString(resolver, name)
                putStringCache(Settings.Secure.ANDROID_ID, androidId)
                return androidId
            }
            return null
        }

        return Settings.System.getString(resolver, name)
    }

    /**
     * getSensorList
     */
    @JvmStatic
    @AsmField(oriClass = SensorManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getSensorList(manager: SensorManager, type: Int): MutableList<Sensor>? {
        log("getSensorList,isAgreePrivacy=$isAgreePrivacy")

        if (isAgreePrivacy) {
            return manager.getSensorList(type)
        }
        return null

    }

    /**
     * 读取WIFI扫描结果
     */
    @JvmStatic
    @AsmField(oriClass = WifiManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getScanResults(manager: WifiManager): MutableList<ScanResult>? {
        log("getSensorList,isAgreePrivacy=$isAgreePrivacy")

        if (isAgreePrivacy) {
            return manager.getScanResults()
        }
        return null

    }

    /**
     * 读取DHCP信息
     */
    @JvmStatic
    @AsmField(oriClass = WifiManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getDhcpInfo(manager: WifiManager): DhcpInfo? {
        log("getSensorList,isAgreePrivacy=$isAgreePrivacy")

        if (isAgreePrivacy) {
            return manager.getDhcpInfo()
        }
        return null

    }


    /**
     * 读取DHCP信息
     */
    @SuppressLint("MissingPermission")
    @JvmStatic
    @AsmField(oriClass = WifiManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getConfiguredNetworks(manager: WifiManager): MutableList<WifiConfiguration>? {
        log("getSensorList,isAgreePrivacy=$isAgreePrivacy")

        if (isAgreePrivacy) {
            return manager.getConfiguredNetworks()
        }
        return null

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
        log("getLastKnownLocation: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy) {
            return manager.getLastKnownLocation(provider)
        }
        return null
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
        log("requestLocationUpdates: isAgreePrivacy=$isAgreePrivacy")
        if (isAgreePrivacy) {
            return manager.requestLocationUpdates(provider, minTime, minDistance, listener)
        }
    }


    private fun log(log: String) {
        Log.i(TAG, log)
    }
}