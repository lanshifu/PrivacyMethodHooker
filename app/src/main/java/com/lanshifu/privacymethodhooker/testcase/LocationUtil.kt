package com.lanshifu.privacymethodhooker.testcase

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

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

    @RequiresApi(Build.VERSION_CODES.M)
    fun getLatitude(context: Activity): Double? {
        return getLastKnownLocation(context)?.latitude
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getLongitude(context: Activity): Double? {
        return getLastKnownLocation(context)?.longitude
    }
}