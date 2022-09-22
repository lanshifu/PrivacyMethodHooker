package com.lanshifu.privacymethodhooker.testcase

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.CellInfo
import android.telephony.CellLocation
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.lanshifu.privacymethodhooker.Myapp

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
@SuppressLint("NewApi", "StaticFieldLeak")
object TelePhonyManagerTest {
    private var manager: TelephonyManager =
        Myapp.context.getSystemService(TelephonyManager::class.java) as TelephonyManager

    private val context = Myapp.context

    fun getImei(): String? {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //getImeiForSlot: The user 10191 does not meet the requirements to access device identifiers.
//            manager.getImei()
            null
        } else {
            "VERSION.SDK_INT < O"
        }
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

    fun getSubscriberId(): String? {
        return manager.getSubscriberId()
    }

    @SuppressLint("HardwareIds")
    fun getSimSerialNumber(): String? {
        return manager.simSerialNumber
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


//    fun getNeighboringCellInfo(): String? {
//        return manager.getNeighboringCellInfo()
//    }


    fun getSimOperator(): String? {
        return manager.getSimOperator()
    }

    fun getSimOperatorName(): String? {
        return manager.getSimOperatorName()
    }

    fun getSimCountryIso(): String? {
        return manager.getSimCountryIso()
    }

    fun getNetworkOperator(): String? {
        return manager.getNetworkOperator()
    }

    fun getNetworkOperatorName(): String? {
        return manager.getNetworkOperatorName()
    }

    fun getNetworkCountryIso(): String? {
        return manager.getNetworkCountryIso()
    }


    fun getMeid(): String? {
        return manager.getMeid()
    }


}