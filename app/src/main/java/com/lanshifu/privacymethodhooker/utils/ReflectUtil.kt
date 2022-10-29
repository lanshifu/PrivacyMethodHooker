package com.lanshifu.privacymethodhooker.utils

import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import com.lanshifu.privacymethodhooker.MyApp
import com.lanshifu.privacy_method_hook_library.log.LogUtil
import java.lang.reflect.Method

/**
 * @author lanxiaobin
 * @date 2022/10/11
 */
object ReflectUtil {

    @RequiresApi(Build.VERSION_CODES.M)
    fun test() {

        val manager: TelephonyManager =
            MyApp.context.getSystemService(TelephonyManager::class.java) as TelephonyManager

        try {
            val clazz = Class.forName("android.telephony.TelephonyManager")
            val method: Method = clazz.getDeclaredMethod("getSimOperator")
            method.isAccessible = true
            val getSimOperator = method.invoke(manager) as String?
            LogUtil.d("ReflectUtil", "getSimOperator= :$getSimOperator")
        } catch (e: Exception) {
            LogUtil.d("ReflectUtil", "getSimOperator,Exception")
            e.printStackTrace()
        }

        try {
            val clazz = Class.forName("com.lanshifu.privacymethodhooker.utils.TestClass")
            val method: Method =
                clazz.getDeclaredMethod("getFromTwoParam", Int::class.java, Int::class.java)
            method.isAccessible = true
            val getAidForAppType = method.invoke(clazz.newInstance(), 1 ,1) as Int?
            LogUtil.d("ReflectUtil", "getFromTwoParam=$getAidForAppType")
        } catch (e: Exception) {
            LogUtil.d("ReflectUtil", "getFromTwoParam,Exception")
            e.printStackTrace()
        }



        try {
            val clazz = Class.forName("android.os.SystemProperties")
            val methodget: Method = clazz.getMethod("get", String::class.java)
            methodget.setAccessible(true)
            val level = methodget.invoke(null, "ro.serialno") as String?
            LogUtil.d("", "serialno= :$level")
        } catch (e: Exception) {
            LogUtil.d("", "serialno= Exception")
            e.printStackTrace()
        }


    }
}

class TestClass {

    fun getFromTwoParam(param1: Int, param2: Int): Int {
        return param1 + param2
    }
}