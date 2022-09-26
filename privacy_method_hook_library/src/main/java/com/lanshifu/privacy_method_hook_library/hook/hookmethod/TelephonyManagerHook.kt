package com.lanshifu.privacy_method_hook_library.hook.hookmethod

import android.annotation.SuppressLint
import android.telephony.CellLocation
import android.telephony.NeighboringCellInfo
import android.telephony.TelephonyManager
import androidx.annotation.Keep
import com.lanshifu.asm_annotation.AsmMethodOpcodes
import com.lanshifu.asm_annotation.AsmMethodReplace
import com.lanshifu.privacy_method_hook_library.hook.checkCacheAndPrivacy
import com.lanshifu.privacy_method_hook_library.hook.saveResult

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
@SuppressLint("HardwareIds", "MissingPermission", "NewApi")
object TelephonyManagerHook {

    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getImei(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        return null
//        val key = "TelephonyManager#getImei()"
//        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
//        if (checkResult.shouldReturn()) {
//            return checkResult.cacheData ?: ""
//        }
//        //getImeiForSlot: The user 10191 does not meet the requirements to access device identifiers.
//        return try {
//            saveResult(key, manager.imei ?: "", callerClassName)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            ""
//        }
    }


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
        return null
//        val key = "TelephonyManager#getImei($slotIndex)"
//        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
//        if (checkResult.shouldReturn()) {
//            return checkResult.cacheData
//        }
//        return saveResult(key, manager.getImei(slotIndex), callerClassName)
    }


    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getDeviceId(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        return null
//        val key = "TelephonyManager#getDeviceId()"
//        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
//        if (checkResult.shouldReturn()) {
//            return checkResult.cacheData ?: ""
//        }
//        //The user 10210 does not meet the requirements to access device identifiers.
//        return try {
//            saveResult(key, manager.deviceId ?: "", callerClassName)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            ""
//        }
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getDeviceId(
        manager: TelephonyManager,
        slotIndex: Int,
        callerClassName: String
    ): String? {
        return null
//        val key = "#TelephonyManagergetDeviceId(${slotIndex})"
//        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
//        if (checkResult.shouldReturn()) {
//            return checkResult.cacheData ?: ""
//        }
//        return saveResult(key, manager.getDeviceId(slotIndex) ?: "", callerClassName)
    }


    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getSubscriberId(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        return null
//        val key = "TelephonyManager#getSubscriberId()"
//        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
//        if (checkResult.shouldReturn()) {
//            return checkResult.cacheData ?: ""
//        }
//        return try {
//            saveResult(key, manager.subscriberId ?: "", callerClassName)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            ""
//        }
    }


    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getSimSerialNumber(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
//        val key = "TelephonyManager#getSimSerialNumber()"
//        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
//        if (checkResult.shouldReturn()) {
//            return checkResult.cacheData ?: ""
//        }
        return null
        //The user 10210 does not meet the requirements to access device identifiers.
//        return try {
//            saveResult(key, manager.simSerialNumber ?: "", callerClassName)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            ""
//        }
    }


    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getMeid(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        return null
//        val key = "TelephonyManager#getMeid()"
//        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
//        if (checkResult.shouldReturn()) {
//            return checkResult.cacheData ?: ""
//        }
//        //The user 10210 does not meet the requirements to access device identifiers.
//        return try {
//            saveResult(key, manager.meid ?: "", callerClassName)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            ""
//        }
    }


    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getLine1Number(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        val key = "TelephonyManager#getLine1Number"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return saveResult(key, manager.line1Number, callerClassName)
    }


    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getCellLocation(
        manager: TelephonyManager,
        callerClassName: String
    ): CellLocation? {
        val key = "TelephonyManager#getCellLocation"
        val checkResult = checkCacheAndPrivacy<CellLocation>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return saveResult(key, manager.cellLocation, callerClassName)
    }


    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    @Deprecated("高版本sdk没有这个方法了，过时了，返回null")
    fun getNeighboringCellInfo(
        manager: TelephonyManager,
        callerClassName: String
    ): List<NeighboringCellInfo>? {
        val key = "getNeighboringCellInfo"
        val checkResult = checkCacheAndPrivacy<List<NeighboringCellInfo>>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
//        return saveResult(key, manager.getNeighboringCellInfo(), callerClassName)
        return null
    }


    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getSimOperator(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        val key = "getSimOperator"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return saveResult(key, manager.simOperator, callerClassName)
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getSimOperatorName(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        val key = "getSimOperatorName"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return saveResult(key, manager.simOperatorName, callerClassName)
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getSimCountryIso(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        val key = "TelephonyManager#getSimCountryIso"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return saveResult(key, manager.simCountryIso, callerClassName)
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getNetworkOperator(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        val key = "getNetworkOperator"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return saveResult(key, manager.networkOperator, callerClassName)
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getNetworkOperatorName(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        val key = "getNetworkOperatorName"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return saveResult(key, manager.networkOperatorName, callerClassName)
    }

    @JvmStatic
    @AsmMethodReplace(
        oriClass = TelephonyManager::class,
        oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    fun getNetworkCountryIso(
        manager: TelephonyManager,
        callerClassName: String
    ): String? {
        val key = "getNetworkCountryIso"
        val checkResult = checkCacheAndPrivacy<String>(key, callerClassName)
        if (checkResult.shouldReturn()) {
            return checkResult.cacheData
        }
        return saveResult(key, manager.networkCountryIso, callerClassName)
    }

}
