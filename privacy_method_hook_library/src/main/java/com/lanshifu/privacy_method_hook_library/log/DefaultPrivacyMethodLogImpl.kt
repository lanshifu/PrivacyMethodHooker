package com.lanshifu.privacy_method_hook_library.log

import android.util.Log

/**
 * @author lanxiaobin
 * @date 2022/9/21
 */
class DefaultPrivacyMethodLogImpl : IPrivacyMethodLog {

    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun e(tag: String, message: String) {
        Log.e(tag, message)
    }
}