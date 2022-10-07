package com.lanshifu.privacymethodhooker.testcase

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.lanshifu.privacymethodhooker.MyApp

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
object SensorUtil {

    //SensorManager实例
    @RequiresApi(Build.VERSION_CODES.M)

    private var manager: SensorManager =
        MyApp.context.getSystemService(SensorManager::class.java) as SensorManager


    fun getSensorList(context: Activity): MutableList<Sensor>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.getSensorList(Sensor.TYPE_ALL)
        } else {
            null
        }
    }

    fun getName(context: Activity): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (sensor in manager.getSensorList(Sensor.TYPE_ALL) ?: emptyList()) {
                return sensor.name
            }
        } else {
            return  null
        }
        return  null
    }

    fun getType(context: Activity): Int? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (sensor in manager.getSensorList(Sensor.TYPE_ALL) ?: emptyList()) {
                return sensor.type
            }
        } else {
            return  null
        }
        return  null
    }

    fun getVersion(context: Activity): Int? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (sensor in manager.getSensorList(Sensor.TYPE_ALL) ?: emptyList()) {
                return sensor.version
            }
        } else {
            return  null
        }
        return  null
    }

}