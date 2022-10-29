package com.lanshifu.privacy_method_hook_library

import android.annotation.SuppressLint
import android.content.Context
import com.lanshifu.privacy_method_hook_library.delegate.DefaultPrivacyMethodManagerDelegate
import com.lanshifu.privacy_method_hook_library.delegate.IPrivacyMethodManagerDelegate
import com.lanshifu.privacy_method_hook_library.log.LogUtil

/**
 *
 * 隐私方法管理类
 * init方法提供给外部调用
 */
@SuppressLint("StaticFieldLeak")
object PrivacyMethodManager {

    var mContext: Context? = null

    private var mDelegate: IPrivacyMethodManagerDelegate =
        object : DefaultPrivacyMethodManagerDelegate() {
            override fun isAgreePrivacy(): Boolean {
                return true
            }

            override fun isDebugMode(): Boolean {
                return true
            }

        }

    fun init(content: Context, delegate: IPrivacyMethodManagerDelegate) {
        mDelegate = delegate
        mContext = content.applicationContext

        delegate.customLogImpl()?.let {
            LogUtil.logImpl = it
        }
        LogUtil.i("PrivacyMethodManager","init")
    }

    fun getDelegate(): IPrivacyMethodManagerDelegate {
        return mDelegate
    }

}