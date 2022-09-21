package com.lanshifu.privacy_method_hook_library.log

/**
 * @author lanxiaobin
 * @date 2022/9/21
 */
object LogUtil : IPrivacyMethodLog {

    var logImpl = DefaultPrivacyMethodLogImpl()

    override fun d(message: String) {
        logImpl.d(message)
    }

    override fun i(message: String) {
        logImpl.i(message)
    }

    override fun w(message: String) {
        logImpl.w(message)
    }

    override fun e(message: String) {
        logImpl.e(message)
    }
}