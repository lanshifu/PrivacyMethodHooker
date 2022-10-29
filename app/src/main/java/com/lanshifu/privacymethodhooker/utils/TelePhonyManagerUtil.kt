package com.lanshifu.privacymethodhooker.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.CellInfo
import android.telephony.CellLocation
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import com.lanshifu.privacymethodhooker.MyApp
import java.lang.reflect.Method

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
@SuppressLint("NewApi", "StaticFieldLeak")
object TelePhonyManagerUtil {
    private var manager: TelephonyManager =
        MyApp.context.getSystemService(TelephonyManager::class.java) as TelephonyManager

    private val context = MyApp.context

    fun getImei(): String? {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //getImeiForSlot: The user 10191 does not meet the requirements to access device identifiers.
            manager.getImei()
            null
        } else {
            "VERSION.SDK_INT < O"
        }
    }
    fun getImeiByReflect(): String? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getImei")
        method.isAccessible = true
        return method.invoke(manager) as String?

    }
    fun getImei2(): String? {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //getImeiForSlot: The user 10191 does not meet the requirements to access device identifiers.
            manager.getImei(0)
            null
        } else {
            "VERSION.SDK_INT < O"
        }
    }
    fun getImei2ByReflect(): String? {

        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getImei",Int::class.java)
        method.isAccessible = true
        return method.invoke(manager,0) as String?
    }

    fun getDeviceId(context: Activity): String? {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            context.requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), 0)
            return null
        }
        //The user 10191 does not meet the requirements to access device identifiers
        return manager.getDeviceId()
    }
    fun getDeviceIdByReflect(context: Activity): String? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getDeviceId")
        method.isAccessible = true
        return method.invoke(manager) as String?
    }

    fun getDeviceIdWithSlotIndex(context: Activity): String? {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            context.requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), 0)
            return null
        }
        //The user 10191 does not meet the requirements to access device identifiers
        return manager.getDeviceId(0)
    }

    fun getDeviceIdWithSlotIndexByReflect(context: Activity): String? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getDeviceId",Int::class.java)
        method.isAccessible = true
        return method.invoke(manager,0) as String?
    }

    fun getSubscriberId(): String? {
        return manager.getSubscriberId()
    }

    fun getSubscriberIdByReflect(): String? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getSubscriberId")
        method.isAccessible = true
        return method.invoke(manager) as String?
    }

    @SuppressLint("HardwareIds")
    fun getSimSerialNumber(): String? {
        return manager.simSerialNumber
    }
    fun getSimSerialNumberByReflect(): String? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getSimSerialNumber")
        method.isAccessible = true
        return method.invoke(manager) as String?
    }

    fun getLine1Number(): String? {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_NUMBERS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return ""
        }
        return manager.getLine1Number()
    }
    fun getLine1NumberByReflect(): String? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getLine1Number")
        method.isAccessible = true
        return method.invoke(manager) as String?
    }


    fun getCellLocation(): CellLocation? {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }
        return manager.getCellLocation()
    }

    fun getCellLocationByReflect(): CellLocation? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getCellLocation")
        method.isAccessible = true
        return method.invoke(manager) as CellLocation?
    }


    fun getAllCellInfo(context: Activity): List<CellInfo>? {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            context.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),3)
            return null
        }
        return  manager.getAllCellInfo()
    }


    fun getSimOperator(): String? {
        return manager.getSimOperator()
    }

    fun getSimOperatorByReflect(): String? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getSimOperator")
        method.isAccessible = true
        return method.invoke(manager) as String?
    }

    fun getSimOperatorName(): String? {
        return manager.getSimOperatorName()
    }
    fun getSimOperatorNameByReflect(): String? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getSimOperatorName")
        method.isAccessible = true
        return method.invoke(manager) as String?
    }

    fun getSimCountryIso(): String? {
        return manager.getSimCountryIso()
    }
    fun getSimCountryIsoByReflect(): String? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getSimCountryIso")
        method.isAccessible = true
        return method.invoke(manager) as String?
    }

    fun getNetworkOperator(): String? {
        return manager.getNetworkOperator()
    }
    fun getNetworkOperatorByReflect(): String? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getNetworkOperator")
        method.isAccessible = true
        return method.invoke(manager) as String?
    }

    fun getNetworkOperatorName(): String? {
        return manager.getNetworkOperatorName()
    }

    fun getNetworkOperatorNameByReflect(): String? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getNetworkOperatorName")
        method.isAccessible = true
        return method.invoke(manager) as String?
    }

    fun getNetworkCountryIso(): String? {
        return manager.getNetworkCountryIso()
    }

    fun getNetworkCountryIsoByReflect(): String? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getNetworkCountryIso")
        method.isAccessible = true
        return method.invoke(manager) as String?
    }


    fun getMeid(): String? {
        return manager.getMeid()
    }

    fun getMeidByReflect(): String? {
        val clazz = Class.forName("android.telephony.TelephonyManager")
        val method: Method = clazz.getDeclaredMethod("getMeid")
        method.isAccessible = true
        return method.invoke(manager) as String?
    }


}