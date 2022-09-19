package com.lanshifu.privacy_method_hook_library.hook

import android.annotation.SuppressLint
import android.app.ActivityManager
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
import android.provider.Settings
import android.telephony.CellInfo
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.asm_annotation.BuildConfig
import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager

/**
 * @author lanxiaobin
 * @date 2022/9/5
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
object PrivacyMethodUtil {

    private var anyCache = HashMap<String, Any>()

    private fun checkAgreePrivacy(name: String, className: String = ""): Boolean {

        var methodStack = ""
        if (PrivacyMethodManager.isShowPrivacyMethodStack()) {
            methodStack = Log.getStackTraceString(Throwable())
        }

        PrivacyMethodManager.onPrivacyMethodCall(className, name, methodStack)

        if (!PrivacyMethodManager.isAgreePrivacy()) {
            //没有同意隐私权限，打印堆栈，toast
            PrivacyMethodManager.onPrivacyMethodCallIllegal(className, name, methodStack)
            return false
        }
        return true
    }

    private fun <T> getListCache(key: String): List<T>? {
        if (!PrivacyMethodManager.isUseCache()) {
            return null
        }
        val cache = anyCache[key]
        if (cache != null && cache is List<*>) {
            try {
                return cache as List<T>
            } catch (e: Exception) {
                Log.w(PrivacyMethodManager.TAG, "getListCache: key=$key,e=${e.message}")
            }
        }
        logD("getListCache key=$key,return null")
        return null
    }

    private fun <T> getCache(key: String): T? {
        if (!PrivacyMethodManager.isUseCache()) {
            return null
        }
        val cache = anyCache[key]
        if (cache != null) {
            try {
                Log.d(PrivacyMethodManager.TAG, "getCache: key=$key,value=$cache")
                return cache as T
            } catch (e: Exception) {
                Log.w(PrivacyMethodManager.TAG, "getListCache: key=$key,e=${e.message}")
            }
        }
        logD("getCache key=$key,return null")
        return null
    }


    private fun <T> putCache(key: String, value: T): T {
        logI("putCache key=$key,value=$value")
        value?.let {
            anyCache[key] = value
        }
        return value
    }


    @JvmStatic
    @AsmMethodReplace(oriClass = ActivityManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getRunningAppProcesses(
        callerClassName: String,
        manager: ActivityManager
    ): List<ActivityManager.RunningAppProcessInfo?> {
        val key = "getRunningAppProcesses"
        val cache = getListCache<ActivityManager.RunningAppProcessInfo?>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return emptyList()
        }
        val value = manager.runningAppProcesses
        return putCache(key, value)
    }

    @JvmStatic
    @AsmMethodReplace(oriClass = ActivityManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getRecentTasks(
        callerClassName: String,
        manager: ActivityManager,
        maxNum: Int,
        flags: Int
    ): List<ActivityManager.RecentTaskInfo>? {
        val key = "getRecentTasks"
        val cache = getListCache<ActivityManager.RecentTaskInfo>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return emptyList()
        }
        val value = manager.getRecentTasks(maxNum, flags)
        return putCache(key, value)

    }

    @JvmStatic
    @AsmMethodReplace(oriClass = ActivityManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getRunningTasks(
        callerClassName: String,
        manager: ActivityManager,
        maxNum: Int
    ): List<ActivityManager.RunningTaskInfo>? {
        val key = "getRunningTasks"
        val cache = getListCache<ActivityManager.RunningTaskInfo>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return emptyList()
        }
        val value = manager.getRunningTasks(maxNum)
        return putCache(key, value)

    }

    /**
     * 读取基站信息，需要开启定位
     */
    @JvmStatic
    @SuppressLint("MissingPermission")
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getAllCellInfo(callerClassName: String, manager: TelephonyManager): List<CellInfo>? {
        val key = "getAllCellInfo"
        val cache = getListCache<CellInfo>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return emptyList()
        }
        val value = manager.getAllCellInfo()
        return putCache(key, value)
    }

    /**
     * 读取基站信息
     */
    @SuppressLint("HardwareIds")
    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getDeviceId(callerClassName: String, manager: TelephonyManager): String? {
        val key = "getDeviceId"
        val cache = getCache<String>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return null
        }
        //READ_PHONE_STATE 已经整改去掉，返回null
//        return manager.deviceId
        return putCache(key, null)

    }

    /**
     * 读取基站信息
     */
    @SuppressLint("HardwareIds")
    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getImei(callerClassName: String, manager: TelephonyManager): String? {
        val key = "getImei"
        val cache = getCache<String>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return null
        }
        //READ_PHONE_STATE 已经整改去掉，返回null
        return putCache(key, null)
    }

    /**
     * 读取ICCID
     */
    @SuppressLint("HardwareIds")
    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getSimSerialNumber(callerClassName: String, manager: TelephonyManager): String? {
        val key = "getSimSerialNumber"
        val cache = getCache<String>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return ""
        }
        //android.Manifest.permission.READ_PHONE_STATE,不允许App读取，拦截调用
        return putCache(key, null)
    }

    /**
     * 读取WIFI的SSID
     */
    @JvmStatic
    @AsmMethodReplace(oriClass = WifiInfo::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getSSID(callerClassName: String, manager: WifiInfo): String? {
        val key = "getSSID"
        val cache = getCache<String?>(key)
        if (cache != null) {
            return cache
        }

        if (!checkAgreePrivacy(key)) {
            return ""
        }

        val value = manager.ssid
        return putCache(key, value)
    }

    /**
     * 读取WIFI的SSID
     */
    @JvmStatic
    @AsmMethodReplace(oriClass = WifiInfo::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getBSSID(callerClassName: String, manager: WifiInfo): String? {
        val key = "getBSSID"
        val cache = getCache<String?>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return ""
        }
        val value = manager.bssid
        return putCache(key, value)
    }

    /**
     * 读取WIFI的SSID
     */
    @SuppressLint("HardwareIds", "MissingPermission")
    @JvmStatic
    @AsmMethodReplace(oriClass = WifiInfo::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getMacAddress(callerClassName: String, manager: WifiInfo): String? {
        val key = "getMacAddress"
        val cache = getCache<String?>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return ""
        }
        val value = manager.macAddress
        return putCache(key, value)
    }

    /**
     * 读取AndroidId
     */
    @JvmStatic
    @AsmMethodReplace(oriClass = Settings.System::class, oriAccess = AsmMethodOpcodes.INVOKESTATIC)
    fun getString(callerClassName: String, resolver: ContentResolver, name: String): String? {
        //处理AndroidId
        if (Settings.Secure.ANDROID_ID == name) {

            val key = "ANDROID_ID"
            val cache = getCache<String?>(key)
            if (cache != null) {
                return cache
            }
            if (!checkAgreePrivacy(key)) {
                return ""
            }
            val value = Settings.System.getString(resolver, name)
            return putCache(key, value)
        }

        return Settings.System.getString(resolver, name)
    }

    /**
     * getSensorList
     */
    @JvmStatic
    @AsmMethodReplace(oriClass = SensorManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getSensorList(callerClassName: String, manager: SensorManager, type: Int): List<Sensor>? {
        val key = "getSensorList"
        val cache = getListCache<Sensor>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return mutableListOf()
        }
        val value = manager.getSensorList(type)
        return putCache(key, value)

    }

    /**
     * 读取WIFI扫描结果
     */
    @JvmStatic
    @AsmMethodReplace(oriClass = WifiManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getScanResults(callerClassName: String, manager: WifiManager): List<ScanResult>? {
        val key = "getScanResults"
        val cache = getListCache<ScanResult>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy("getScanResults")) {
            return mutableListOf()
        }
        val value = manager.getScanResults()
        return putCache(key, value)
    }

    /**
     * 读取DHCP信息
     */
    @JvmStatic
    @AsmMethodReplace(oriClass = WifiManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getDhcpInfo(callerClassName: String, manager: WifiManager): DhcpInfo? {
        val key = "getDhcpInfo"
        val cache = getCache<DhcpInfo>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return null
        }
        val value = manager.getDhcpInfo()
        return putCache(key, value)

    }


    /**
     * 读取DHCP信息
     */
    @SuppressLint("MissingPermission")
    @JvmStatic
    @AsmMethodReplace(oriClass = WifiManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getConfiguredNetworks(callerClassName: String, manager: WifiManager): List<WifiConfiguration>? {
        val key = "getConfiguredNetworks"
        val cache = getListCache<WifiConfiguration>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return mutableListOf()
        }
        val value = manager.getConfiguredNetworks()
        return putCache(key, value)

    }


    /**
     * 读取位置信息
     */
    @JvmStatic
    @SuppressLint("MissingPermission")
    @AsmMethodReplace(oriClass = LocationManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun getLastKnownLocation(
        callerClassName: String,
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
    @AsmMethodReplace(oriClass = LocationManager::class, oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL)
    fun requestLocationUpdates(
        callerClassName: String,
        manager: LocationManager, provider: String, minTime: Long, minDistance: Float,
        listener: LocationListener
    ) {
        if (!checkAgreePrivacy("requestLocationUpdates")) {
            return
        }
        manager.requestLocationUpdates(provider, minTime, minDistance, listener)

    }


    private fun logI(log: String) {
        Log.i(PrivacyMethodManager.TAG, log)
    }

    private fun logD(log: String) {
        if (BuildConfig.DEBUG) {
            Log.d(PrivacyMethodManager.TAG, log)
        }
    }

    private fun logW(log: String) {
        Log.w(PrivacyMethodManager.TAG, log)
    }

}