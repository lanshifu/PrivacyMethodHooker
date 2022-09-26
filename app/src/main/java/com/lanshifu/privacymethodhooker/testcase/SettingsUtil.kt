package com.lanshifu.privacymethodhooker.testcase

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.Settings

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
object SettingsUtil {

    fun getAndroidBySystem(contentResolver: ContentResolver):String{
        return Settings.System.getString(contentResolver,"android_id")
    }

    @SuppressLint("HardwareIds")
    fun getAndroidIdBySecure(contentResolver: ContentResolver):String{
        return Settings.Secure.getString(contentResolver,"android_id")
    }

    fun getBluetoothAddress(contentResolver: ContentResolver):String{
        return Settings.Secure.getString(contentResolver,"bluetooth_address")
    }

    fun getBluetoothName(contentResolver: ContentResolver):String{
        return Settings.Secure.getString(contentResolver,"bluetooth_name")
    }
}