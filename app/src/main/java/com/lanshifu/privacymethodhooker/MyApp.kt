package com.lanshifu.privacymethodhooker

import android.app.Application
import android.content.Context

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
open class MyApp : Application() {
    companion object {
        lateinit var context: Context

        ///
        var isAgreePrivacy = false
    }

    override fun onCreate() {
        context = this
        //初始化
        PrivacyMethodInitTask.init(this)

        super.onCreate()

    }

}