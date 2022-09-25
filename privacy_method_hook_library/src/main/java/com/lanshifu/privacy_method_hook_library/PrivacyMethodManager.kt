package com.lanshifu.privacy_method_hook_library

import android.annotation.SuppressLint
import android.content.Context
import com.lanshifu.privacy_method_hook_library.delegate.DefaultPrivacyMethodManagerDelegate
import com.lanshifu.privacy_method_hook_library.delegate.PrivacyMethodManagerDelegate

/**
 *
 * 隐私方法管理类
 * 提供给外部调用，设置代理
 */
@SuppressLint("StaticFieldLeak")
object PrivacyMethodManager : PrivacyMethodManagerDelegate {

    private var mDelegate: PrivacyMethodManagerDelegate = DefaultPrivacyMethodManagerDelegate()

    lateinit var mContext: Context

    fun init(content: Context, delegate: PrivacyMethodManagerDelegate) {
        mDelegate = delegate
        mContext = content.applicationContext
    }

    override fun isAgreePrivacy(): Boolean {
        return mDelegate.isAgreePrivacy()
    }

    override fun isUseCache(methodName: String): Boolean {
        return mDelegate.isUseCache(methodName)
    }

    override fun isShowPrivacyMethodStack(): Boolean {
        return mDelegate.isShowPrivacyMethodStack()
    }

    override fun onPrivacyMethodCall(className: String, methodName: String, methodStack: String) {
        mDelegate.onPrivacyMethodCall(className, methodName, methodStack)
    }

    override fun onPrivacyMethodCallIllegal(
        className: String,
        methodName: String,
        methodStack: String
    ) {
        mDelegate.onPrivacyMethodCallIllegal(className, methodName, methodStack)
    }

    override fun onCacheExpire(methodName: String) {
        mDelegate.onCacheExpire(methodName)
    }

    override fun customCacheExpireMap(): HashMap<String, Int> {
        return mDelegate.customCacheExpireMap()
    }

}