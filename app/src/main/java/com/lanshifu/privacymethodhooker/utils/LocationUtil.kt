package com.lanshifu.privacymethodhooker.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import java.lang.reflect.Method

/**
 * @author lanxiaobin
 * @date 2022/9/27
 */
object LocationUtil {

    @RequiresApi(Build.VERSION_CODES.M)
    fun getLastKnownLocation(context: Activity): Location? {

        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val provider = manager.getProviders(true)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            context.requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 0
            )
            return null
        }
        Log.i("TAG", "getLastKnownLocation: provider.size=${provider.size}")
        if (provider.size > 0) {
            for (provider in provider) {
                return manager.getLastKnownLocation(provider)
            }
        }
        return manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.M)
    fun getLastKnownLocationByReflect(context: Activity): Location? {

        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = manager.getProviders(true)

        Log.i("TAG", "getLastKnownLocation: provider.size=${providers.size}")
        if (providers.size > 0) {
            for (provider in providers) {
                val clazz = Class.forName("android.location.LocationManager")
                val method: Method = clazz.getDeclaredMethod("getLastKnownLocation",String::class.java)
                method.isAccessible = true
                return method.invoke(manager,provider) as Location?
            }
        }
        val clazz = Class.forName("android.location.LocationManager")
        val method: Method = clazz.getDeclaredMethod("getLastKnownLocation")
        method.isAccessible = true
        return method.invoke(manager,LocationManager.GPS_PROVIDER) as Location?

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getLatitude(context: Activity): Double? {
        return getLastKnownLocation(context)?.latitude
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getLatitudeByReflect(context: Activity): Double? {

        return getLastKnownLocationByReflect(context)?.latitude
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getLongitude(context: Activity): Double? {
        return getLastKnownLocation(context)?.longitude
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getLongitudeByReflect(context: Activity): Double? {
        return getLastKnownLocationByReflect(context)?.longitude
    }
}