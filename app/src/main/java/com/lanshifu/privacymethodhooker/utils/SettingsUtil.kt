package com.lanshifu.privacymethodhooker.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.Settings
import java.lang.reflect.Method

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
object SettingsUtil {

    fun getAndroidBySystem(contentResolver: ContentResolver): String {
        return Settings.System.getString(contentResolver, "android_id")
    }

    fun getAndroidBySystemByReflect(contentResolver: ContentResolver): String {
        val clazz = Class.forName("android.provider.Settings\$System")
        val method: Method =
            clazz.getDeclaredMethod("getString", ContentResolver::class.java, String::class.java)
        method.isAccessible = true
        return method.invoke(null, contentResolver, "android_id") as String
    }

    @SuppressLint("HardwareIds")
    fun getAndroidIdBySecure(contentResolver: ContentResolver): String {
        return Settings.Secure.getString(contentResolver, "android_id")
    }

    fun getAndroidIdBySecureByReflect(contentResolver: ContentResolver): String {
        val clazz = Class.forName("android.provider.Settings\$Secure")
        val method: Method =
            clazz.getDeclaredMethod("getString", ContentResolver::class.java, String::class.java)
        method.isAccessible = true
        return method.invoke(null, contentResolver, "android_id") as String
    }

    fun getBluetoothAddress(contentResolver: ContentResolver): String {
        return Settings.Secure.getString(contentResolver, "bluetooth_address")
    }

    fun getBluetoothAddressByReflect(contentResolver: ContentResolver): String {
        val clazz = Class.forName("android.provider.Settings\$Secure")
        val method: Method =
            clazz.getDeclaredMethod("getString", ContentResolver::class.java, String::class.java)
        method.isAccessible = true
        return method.invoke(null, contentResolver, "bluetooth_address") as String
    }

    fun getBluetoothName(contentResolver: ContentResolver): String {
        return Settings.Secure.getString(contentResolver, "bluetooth_name")
    }
    fun getBluetoothNameByReflect(contentResolver: ContentResolver): String {
        val clazz = Class.forName("android.provider.Settings\$Secure")
        val method: Method =
            clazz.getDeclaredMethod("getString", ContentResolver::class.java, String::class.java)
        method.isAccessible = true
        return method.invoke(null, contentResolver, "bluetooth_name") as String
    }
}