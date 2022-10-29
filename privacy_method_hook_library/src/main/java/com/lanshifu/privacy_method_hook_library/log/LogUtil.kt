package com.lanshifu.privacy_method_hook_library.log

import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager

/**
 * @author lanxiaobin
 * @date 2022/9/21
 */
object LogUtil : IPrivacyMethodLog {

    var logImpl: IPrivacyMethodLog = DefaultPrivacyMethodLogImpl()

    private const val TAG = "PrivacyMethod"

    override fun d(tag: String, message: String) {
        if (!PrivacyMethodManager.getDelegate().isDebugMode()) {
            return
        }
        logImpl.d("[$TAG] $tag", message)
    }

    override fun i(tag: String, message: String) {
        logImpl.i("[$TAG] $tag", message)
    }

    override fun w(tag: String, message: String) {
        logImpl.w("[$TAG] $tag", message)
    }

    override fun e(tag: String, message: String) {
        logImpl.e("[$TAG] $tag", message)
    }
}