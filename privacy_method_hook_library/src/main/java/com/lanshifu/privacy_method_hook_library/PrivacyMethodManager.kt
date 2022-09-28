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
object PrivacyMethodManager {

    private var mDelegate: PrivacyMethodManagerDelegate = DefaultPrivacyMethodManagerDelegate()

    lateinit var mContext: Context

    /**
     * 初始化方法，在Application 的onCreate 方法第一行调用这个
     */
    fun init(content: Context, delegate: PrivacyMethodManagerDelegate) {
        mDelegate = delegate
        mContext = content.applicationContext
    }

    fun getDelegate(): PrivacyMethodManagerDelegate {
        return mDelegate
    }

}