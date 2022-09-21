package com.lanshifu.privacy_method_hook_library.hook

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import com.lanshifu.asm_annotation.AsmClassReplace
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.cache.PrivacyMethodCacheManager
import com.lanshifu.privacy_method_hook_library.system.HookFile
import java.io.File

/**
 * @author lanxiaobin
 * @date 2022/9/5
 * 1、不要被混淆
 *
 * 2、Kotlin 的方法必须要使用JvmStatic注解，否则Java调用会报错
 *
 *     java.lang.IncompatibleClassChangeError: The method
 *     'java.lang.String com.lanshifu.privacymethodhooker.utils.PrivacyUtil.getString(android.content.ContentResolver, java.lang.String)'
 *     was expected to be of type static but instead was found to be of type virtual
 *     (declaration of 'com.lanshifu.privacymethodhooker.MainActivity' appears in /data/app/com.lanshifu.privacymethodhooker-2/base.apk)
 */
@Keep
object DeviceIdHook {


    @SuppressLint("HardwareIds", "MissingPermission", "NewApi")
    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getImei(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        val key = "getImei"
        val cache = PrivacyMethodCacheManager.get<String>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return null
        }

        val value = manager.imei
        saveResult(key, value, callerClassName)
        return value
    }

    @SuppressLint("HardwareIds", "MissingPermission", "NewApi")
    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getImei(
        manager: TelephonyManager,
        slotIndex: Int,
        callerClassName: String
    ): String? {
        val key = "getImei"
        val cache = PrivacyMethodCacheManager.get<String>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return null
        }

        val value = manager.getImei(slotIndex)
        saveResult(key, value, callerClassName)
        return value
    }

    /**
     * 读取AndroidId
     */
    @JvmStatic
    @AsmMethodReplace(oriClass = Settings.System::class, oriAccess = AsmMethodOpcodes.INVOKESTATIC)
    fun getString(
        resolver: ContentResolver, name: String,
        callerClassName: String
    ): String? {
        //处理AndroidId
        if (Settings.Secure.ANDROID_ID == name) {

            val key = "ANDROID_ID"
            val cache = PrivacyMethodCacheManager.get<String?>(key)
            if (cache != null) {
                return cache
            }
            if (!checkAgreePrivacy(key)) {
                return ""
            }
            val value = Settings.System.getString(resolver, name)
            saveResult(key, value, callerClassName)
            return value
        }

        return Settings.System.getString(resolver, name)
    }


    @SuppressLint("MissingPermission", "HardwareIds")
    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getSubscriberId(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        val key = "getSubscriberId"
        val cache = PrivacyMethodCacheManager.get<String>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return null
        }

        val value = manager.subscriberId
        saveResult(key, value, callerClassName)
        return value
    }


    @SuppressLint("MissingPermission", "HardwareIds")
    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getSimSerialNumber(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        val key = "getSimSerialNumber"
        val cache = PrivacyMethodCacheManager.get<String>(key)
        if (cache != null) {
            return cache
        }
        if (!checkAgreePrivacy(key)) {
            return null
        }

        val value = manager.simSerialNumber
        saveResult(key, value, callerClassName)
        return value
    }

    /**
     * 文件
     */
    @AsmClassReplace(oriClass = File::class)
    fun hookFile(string: String): File {
        return HookFile(string)
    }


}

@Keep
object AndroidIdHook {

    /**
     * 读取AndroidId 第二种方式
     */
    @JvmStatic
    @AsmMethodReplace(oriClass = Settings.Secure::class, oriAccess = AsmMethodOpcodes.INVOKESTATIC)
    fun getString(
        resolver: ContentResolver, name: String,
        callerClassName: String
    ): String? {
        //处理AndroidId
        if (Settings.Secure.ANDROID_ID == name) {

            val key = "ANDROID_ID"
            val cache = PrivacyMethodCacheManager.get<String?>(key)
            if (cache != null) {
                return cache
            }
            if (!checkAgreePrivacy(key)) {
                return ""
            }
            val value = Settings.System.getString(resolver, name)
            saveResult(key, value, callerClassName)
            return value
        }

        return Settings.System.getString(resolver, name)
    }

}