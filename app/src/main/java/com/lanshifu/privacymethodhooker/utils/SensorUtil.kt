package lanshifu.utils

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.lanshifu.privacymethodhooker.MyApp
import java.lang.reflect.Method

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

    @RequiresApi(Build.VERSION_CODES.M)
    fun getSensorListByReflect(context: Activity): MutableList<Sensor>? {
        val clazz = Class.forName("android.hardware.SensorManager")
        val method: Method = clazz.getDeclaredMethod("getSensorList",Int::class.java)
        method.isAccessible = true
        return method.invoke(manager,Sensor.TYPE_ALL) as MutableList<Sensor>?
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

    @RequiresApi(Build.VERSION_CODES.M)
    fun getNameByReflect(context: Activity): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (sensor in manager.getSensorList(Sensor.TYPE_ALL) ?: emptyList()) {
                val clazz = Class.forName("android.hardware.Sensor")
                val method: Method = clazz.getDeclaredMethod("getName")
                method.isAccessible = true
                return method.invoke(sensor) as String?
            }
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

    @RequiresApi(Build.VERSION_CODES.M)
    fun getTypeByReflect(context: Activity): Int? {
        for (sensor in manager.getSensorList(Sensor.TYPE_ALL) ?: emptyList()) {
            val clazz = Class.forName("android.hardware.Sensor")
            val method: Method = clazz.getDeclaredMethod("getType")
            method.isAccessible = true
            return method.invoke(sensor) as Int?
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

    @RequiresApi(Build.VERSION_CODES.M)
    fun getVersionByReflect(context: Activity): Int? {
        for (sensor in manager.getSensorList(Sensor.TYPE_ALL) ?: emptyList()) {
            val clazz = Class.forName("android.hardware.Sensor")
            val method: Method = clazz.getDeclaredMethod("getVersion")
            method.isAccessible = true
            return method.invoke(sensor) as Int?
        }
        return  null
    }

}