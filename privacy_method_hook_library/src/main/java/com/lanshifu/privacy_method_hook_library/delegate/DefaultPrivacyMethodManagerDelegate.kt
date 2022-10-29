package com.lanshifu.privacy_method_hook_library.delegate

import android.widget.Toast
import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.cache.IPrivacyMethodCache
import com.lanshifu.privacy_method_hook_library.log.IPrivacyMethodLog
import com.lanshifu.privacy_method_hook_library.log.LogUtil

/**
 * @author lanxiaobin
 * @date 2022/9/5
 */
open abstract class DefaultPrivacyMethodManagerDelegate : IPrivacyMethodManagerDelegate {

    companion object {
        private const val TAG = "DefaultPrivacyMethodManager"
    }

    /**
     * 这些是默认需要缓存的方法，
     * 如果你不要缓存，可以重写 isUseCache 方法返回false
     */
    private val mCacheMethodMap = HashMap<String, String>()

    init {
        arrayOf(
            "Settings\$System#getString(android_id)",
            "Settings\$Secure#getString(android_id)",
            "Settings\$Secure#getString(bluetooth_address)",
            "Settings\$Secure#getString(bluetooth_name)",
            "NetworkInterface#getHardwareAddress",
            "SensorManager#getSensorList",
            "TelephonyManager#getImei",
            "TelephonyManager#getImei(0)",
            "TelephonyManager#getImei(1)",
            "TelephonyManager#getDeviceId",
            "TelephonyManager#getDeviceId(0)",
            "TelephonyManager#getDeviceId(1)",
            "TelephonyManager#getSubscriberId",
            "TelephonyManager#getSimSerialNumber",
            "TelephonyManager#getMeid",
            "TelephonyManager#getSimCountryIso",
            "TelephonyManager#getSimOperator",
            "TelephonyManager#getSimOperatorName",
            "TelephonyManager#getNetworkOperator",
            "TelephonyManager#getNetworkOperatorName",
            "TelephonyManager#getNetworkCountryIso",
            "TelephonyManager#getLine1Number",
            "TelephonyManager#getCellLocation",

            "LocationManager#getLastKnownLocation",

            "PackageManager#getInstalledPackages",
            "PackageManager#getInstalledApplications",

            "WifiManager#getConnectionInfo",
            "WifiManager#getScanResults",
            "WifiManager#getDhcpInfo",

            "WifiInfo#getIpAddress",
            "WifiInfo#getSSID",
            "WifiInfo#getBSSID",
            "WifiInfo#getMacAddress",

            "ActivityManager#getRunningServices",
            "ActivityManager#getRecentTasks",
            "ActivityManager#getRunningTasks",
            "ActivityManager#getRunningAppProcesses"
        ).map {
            mCacheMethodMap[it] = it
        }
    }

    override fun isUseCache(methodName: String, callerClassName: String): Boolean {
        if (mCacheMethodMap.containsKey(methodName) ||
            methodName.startsWith("SystemProperties#get")
        ) {
            return true
        }
        return false
    }

    override fun isShowPrivacyMethodStack(): Boolean {
        return false
    }

    override fun onPrivacyMethodCall(
        callerClassName: String,
        methodName: String,
        methodStack: String
    ) {
        var stack = ""
        if (methodStack.isNotEmpty()) {
            stack = ",methodStack=$methodStack"
        }
        LogUtil.i(
            TAG,
            "onPrivacyMethodCall,className=$callerClassName,methodName=$methodName$stack"
        )
    }

    override fun onPrivacyMethodCallIllegal(
        callerClassName: String,
        methodName: String,
        methodStack: String
    ) {
        LogUtil.w(
            TAG,
            "onPrivacyMethodCallIllegal,callerClassName=$callerClassName，methodName=$methodName,methodStack=$methodStack"
        )

        if (PrivacyMethodManager.getDelegate().isDebugMode()) {
            PrivacyMethodManager.mContext?.let {
                Toast.makeText(
                    it, "存在审核风险【调用了隐私API】\nmethodName: $methodName\nclassName: $callerClassName",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCacheExpire(methodName: String) {
        LogUtil.i(
            TAG,
            "onCacheExpire,methodName=$methodName"
        )
    }

    override fun customCacheExpireTime(): HashMap<String, Int> {

        //设置缓存过期时间，解决频繁索权问题，同时不能影响业务
        // 对业务可能有影响的，时间设置短一点， 否则时间长一点
        val defaultCacheExpireTimeSort = 5
        val defaultCacheExpireTimeLong = 60 * 2

        return HashMap<String, Int>().apply {
            //缓存过期时间(秒)
            this["TelephonyManager#getSimCountryIso"] = defaultCacheExpireTimeLong
            this["TelephonyManager#getSimOperator"] = defaultCacheExpireTimeLong
            this["TelephonyManager#getSimOperatorName"] = defaultCacheExpireTimeLong
            this["TelephonyManager#getNetworkOperator"] = defaultCacheExpireTimeLong
            this["TelephonyManager#getNetworkOperatorName"] = defaultCacheExpireTimeLong
            this["TelephonyManager#getNetworkCountryIso"] = defaultCacheExpireTimeLong
            this["TelephonyManager#getLine1Number"] = defaultCacheExpireTimeSort
            this["TelephonyManager#getCellLocation"] = defaultCacheExpireTimeSort

            this["LocationManager#getLastKnownLocation"] =
                defaultCacheExpireTimeSort //获取定位的频率

            this["PackageManager#getInstalledPackages"] = defaultCacheExpireTimeLong
            this["PackageManager#getInstalledApplications"] = defaultCacheExpireTimeLong

            this["WifiManager#getConnectionInfo"] = defaultCacheExpireTimeSort
            this["WifiManager#getScanResults"] = defaultCacheExpireTimeSort
            this["WifiManager#getDhcpInfo"] = defaultCacheExpireTimeSort

            this["WifiInfo#getIpAddress"] = defaultCacheExpireTimeSort
            this["WifiInfo#getSSID"] = defaultCacheExpireTimeSort
            this["WifiInfo#getBSSID"] = defaultCacheExpireTimeSort
            this["WifiInfo#getMacAddress"] = defaultCacheExpireTimeSort

            this["ActivityManager#getRunningAppProcesses"] = defaultCacheExpireTimeLong
            this["ActivityManager#getRunningServices"] = defaultCacheExpireTimeLong
            this["ActivityManager#getRecentTasks"] = defaultCacheExpireTimeLong
            this["ActivityManager#getRunningTasks"] = defaultCacheExpireTimeLong

            this["ClipboardManager#getPrimaryClip"] =
                defaultCacheExpireTimeLong //限制三方sdk剪贴板读取频率
        }
    }

    override fun customLogImpl(): IPrivacyMethodLog? {
        return null
    }

    override fun customCacheImpl(): IPrivacyMethodCache? {
        return null
    }

    override fun isUseDiskCache(): Boolean {
        return true
    }
}