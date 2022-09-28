package com.lanshifu.privacy_method_hook_library.delegate

import android.widget.Toast
import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.cache.IPrivacyMethodCache
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
        mCacheMethodMap["Settings\$Secure#getString(android_id)"] = "1"
        mCacheMethodMap["Settings\$Secure#getString(bluetooth_address)"] = "1"
        mCacheMethodMap["Settings\$Secure#getString(bluetooth_name)"] = "1"

        mCacheMethodMap["WifiInfo#getMacAddress"] = "1"
        mCacheMethodMap["NetworkInterface#getHardwareAddress"] = "1"

        mCacheMethodMap["SensorManager#getSensorList"] = "1"
    }

    override fun isAgreePrivacy(): Boolean {
        return false
    }

    override fun isDebugMode(): Boolean {
        return true
    }

    override fun isUseCache(methodName: String, callerClassName: String): Boolean {
        if (mCacheMethodMap.containsKey(methodName)) {
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
        LogUtil.d(
            "onPrivacyMethodCall,className=$callerClassName,methodName=$methodName"
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

        if (!PrivacyMethodManager.getDelegate().isDebugMode()) {
            PrivacyMethodManager.mContext?.let {
                Toast.makeText(
                    it, "调用了隐私API，methodName=$methodName，className=$callerClassName",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCacheExpire(methodName: String) {
        LogUtil.i(
            "onCacheExpire,methodName=$methodName"
        )
    }

    override fun customCacheExpireMap(): HashMap<String, Int> {
        return HashMap()
    }

    override fun customCacheImpl(): IPrivacyMethodCache? {
        return null
    }

}