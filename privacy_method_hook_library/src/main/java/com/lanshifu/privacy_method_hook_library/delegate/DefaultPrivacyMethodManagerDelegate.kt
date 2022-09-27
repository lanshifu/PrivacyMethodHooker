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
     * 这些是默认不缓存的方法，黑名单
     * 黑名单中的方法如果需要缓存，可以重写 isUseCache 方法
     */
    private val mCacheBlackMap = HashMap<String, String>()

    init {
        mCacheBlackMap["getSimSerialNumber"] = "1"
        mCacheBlackMap["getLine1Number"] = "1"
        mCacheBlackMap["getCellLocation"] = "1"
        mCacheBlackMap["getSimOperator"] = "1"
        mCacheBlackMap["getSimOperatorName"] = "1"
//        mCacheBlackMap["getSimCountryIso"] = "1"
        mCacheBlackMap["getNetworkOperator"] = "1"
        mCacheBlackMap["getNetworkOperatorName"] = "1"
        mCacheBlackMap["getNetworkCountryIso"] = "1"
        mCacheBlackMap["query"] = "1"
    }

    override fun isAgreePrivacy(): Boolean {
        return false
    }

    override fun isUseCache(methodName: String, callerClassName: String): Boolean {
        if (mCacheBlackMap.containsKey(methodName)) {
            return false
        }
        return true
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