package com.lanshifu.privacy_method_hook_library.delegate

import android.widget.Toast
import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.log.LogUtil

/**
 * @author lanxiaobin
 * @date 2022/9/5
 */
open class DefaultPrivacyMethodManagerDelegate : PrivacyMethodManagerDelegate {

    /**
     * 这些是默认需要缓存的方法，
     * 可以重写 isUseCache 方法
     */
    private val mCacheMethodMap = HashMap<String, String>()

    init {
        mCacheMethodMap["Settings\$System#getString(android_id)"] = "1"
        mCacheMethodMap["Settings\$Secure#getString(bluetooth_address)"] = "1"
        mCacheMethodMap["Settings\$Secure#getString(bluetooth_name)"] = "1"
        mCacheMethodMap["WifiInfo#getMacAddress"] = "1"
        mCacheMethodMap["NetworkInterface#getHardwareAddress"] = "1"
    }

    override fun isAgreePrivacy(): Boolean {
        return false
    }

    override fun isUseCache(methodName: String, callerClassName: String): Boolean {
        if (mCacheMethodMap.containsKey(methodName)) {
            return false
        }
        return false
    }

    override fun isShowPrivacyMethodStack(): Boolean {
        return false
    }

    override fun onPrivacyMethodCall(className: String, methodName: String, methodStack: String) {
        LogUtil.d(
            "onPrivacyMethodCall,className=$className,methodName=$methodName"
        )
    }

    override fun onPrivacyMethodCallIllegal(
        callerClassName: String,
        methodName: String,
        methodStack: String
    ) {
        LogUtil.e(
            "onPrivacyMethodCallIllegal,callerClassName=$callerClassName，methodName=$methodName,methodStack=$methodStack"
        )

        Toast.makeText(
            PrivacyMethodManager.mContext,
            "调用了隐私API，methodName=$methodName，className=$callerClassName",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onCacheExpire(methodName: String) {
        LogUtil.i(
            "onCacheExpire,methodName=$methodName"
        )
    }

    override fun customCacheExpireMap(): HashMap<String, Int> {
        return HashMap<String, Int>().apply {
            ///缓存60s过期
            this["TelephonyManager#getSimCountryIso"] = 10
        }
    }

}