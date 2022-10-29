package com.lanshifu.privacymethodhooker.utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Method

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
object RuntimeUtil {


    fun getSerialNo(): String? {
        try {
            var process = Runtime.getRuntime().exec("getprop ro.serialno")
            val bufferedReader = BufferedReader(
                InputStreamReader(process.getInputStream())
            )
            var line: String?
            var result: String = ""
            while (bufferedReader.readLine().also { line = it } != null) {
                result += line
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }


    fun getEth0(): String? {
        try {
            var process = Runtime.getRuntime().exec("cat /sys/class/net/eth0/address")
            val bufferedReader = BufferedReader(
                InputStreamReader(process.getInputStream())
            )
            var line: String?
            var result: String = ""
            while (bufferedReader.readLine().also { line = it } != null) {
                result += line
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }


    fun getPackage(): String? {
        try {
            var process = Runtime.getRuntime().exec("pm list package")
            val bufferedReader = BufferedReader(
                InputStreamReader(process.getInputStream())
            )
            var line: String?
            var result: String = ""
            while (bufferedReader.readLine().also { line = it } != null) {
                result += line
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    fun execByReflect(string: String): Process {
        val runtime = Runtime.getRuntime()
        val clazz = Class.forName("java.lang.Runtime")
        val method: Method = clazz.getDeclaredMethod("exec", String::class.java)
        method.isAccessible = true
        return method.invoke(runtime, string) as Process
    }

    fun ps(): String? {
        try {
            var process = Runtime.getRuntime().exec("ps")
            val bufferedReader = BufferedReader(
                InputStreamReader(process.getInputStream())
            )
            var line: String?
            var result: String = ""
            while (bufferedReader.readLine().also { line = it } != null) {
                result += line
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }


}