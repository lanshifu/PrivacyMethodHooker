package com.lanshifu.privacy_method_hook_library.log

import android.util.Log

/**
 * @author lanxiaobin
 * @date 2022/9/21
 */
class DefaultPrivacyMethodLogImpl : IPrivacyMethodLog {

    override fun d(message: String) {
        Log.d("PrivacyMethod", message)
    }

    override fun i(message: String) {
        Log.i("PrivacyMethod", message)
    }

    override fun w(message: String) {
        Log.w("PrivacyMethod", message)
    }

    override fun e(message: String) {
        Log.e("PrivacyMethod", message)
    }
}