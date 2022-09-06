package com.lanshifu.privacy_method_hook_library

/**
 *
 * 隐私方法管理类
 * 提供给外部调用，设置代理
 */
object PrivacyMethodManager : IPrivacyMethodManager {

    const val TAG = "PrivacyMethodManager"

    var mDelegate: PrivacyMethodManagerDelegate = DefaultPrivacyMethodManagerDelegate()

    override fun setDelegate(delegate: PrivacyMethodManagerDelegate) {
        mDelegate = delegate
    }

}