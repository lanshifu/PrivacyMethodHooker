package com.lanshifu.privacymethodhooker

import android.app.Application
import android.content.Context

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
class Myapp : Application() {
    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
    }
}