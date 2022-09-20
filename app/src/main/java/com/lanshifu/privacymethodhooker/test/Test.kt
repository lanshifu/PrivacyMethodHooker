package com.lanshifu.privacymethodhooker.test

import android.content.Context

/**
 * @author lanxiaobin
 * @date 2022/9/20
 */
class Test {

    fun testMethodCall(context: Context) {

        TestClass.getAppProcess(context)

        TestClass.getAppProcess(context, "caller")
    }

}


object TestClass {

    fun getAppProcess(context: Context) {

    }

    fun getAppProcess(
        context: Context,
        callerClassName: String
    ) {

    }
}


