package com.lanshifu.privacymethodhooker.testcase

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.DhcpInfo
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Process
import android.telephony.CellInfo
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

/**
 * @author lanxiaobin
 * @date 2020-04-25
 */

fun Context.getCurrentProcessName(): String? {
    val pid = Process.myPid()
    var processName = ""
    val manager: ActivityManager =
        applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningAppProcesses = manager.runningAppProcesses
    for (process in runningAppProcesses) {

        if (process.pid == pid) {
            processName = process.processName
        }
    }
    return processName
}

fun getRunningAppProcesses(context: Context): MutableList<RunningAppProcessInfo?>? {
    val manager: ActivityManager =
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return manager.runningAppProcesses
}

fun getRecentTasks(context: Context): MutableList<ActivityManager.RecentTaskInfo?>? {
    val manager: ActivityManager =
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return manager.getRecentTasks(100,ActivityManager.RECENT_WITH_EXCLUDED)
}

fun getRunningTasks(context: Context): MutableList<ActivityManager.RunningTaskInfo?>? {
    val manager: ActivityManager =
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return manager.getRunningTasks(100)
}

@RequiresApi(Build.VERSION_CODES.M)
fun getAllCellInfo(context: Activity): MutableList<CellInfo>? {
    val manager: TelephonyManager =
        context.getSystemService(TelephonyManager::class.java) as TelephonyManager
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        context.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),0)
        return null
    }
    return manager.getAllCellInfo()
}


@SuppressLint("HardwareIds")
@RequiresApi(Build.VERSION_CODES.M)
fun getDeviceId(context: Activity): String? {
    val manager: TelephonyManager =
        context.getSystemService(TelephonyManager::class.java) as TelephonyManager
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_PHONE_STATE
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        context.requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE),0)
        return null
    }
    return manager.getDeviceId()
}

@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("HardwareIds")
fun getImei(context: Activity): String? {
    val manager: TelephonyManager =
        context.getSystemService(TelephonyManager::class.java) as TelephonyManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        manager.getImei()
    } else {
        "VERSION.SDK_INT < O"
    }
}


@RequiresApi(Build.VERSION_CODES.M)
fun getSensorList(context: Activity): MutableList<Sensor>? {
    val manager: SensorManager =
        context.getSystemService(SensorManager::class.java) as SensorManager

    return manager.getSensorList(Sensor.TYPE_ALL)
}

private fun getWifiInfo(context: Activity): WifiInfo? {

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_WIFI_STATE
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),0)
            return null
        }
    }

    val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    if (networkInfo.isConnected) {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.connectionInfo
    }
    return null
}

fun getSSID(context: Activity): String? {
    return getWifiInfo(context)?.bssid
}

fun getBSSID(context: Activity): String? {
    return getWifiInfo(context)?.bssid
}

fun getMacAddress(context: Activity): String? {
    return getWifiInfo(context)?.macAddress
}

@RequiresApi(Build.VERSION_CODES.M)
fun getScanResults(context: Activity): MutableList<ScanResult>? {
    val wifiManager = context.getSystemService(WifiManager::class.java) as WifiManager
    return wifiManager.scanResults
}

@RequiresApi(Build.VERSION_CODES.M)
fun getDhcpInfo(context: Activity): DhcpInfo? {
    val wifiManager = context.getSystemService(WifiManager::class.java) as WifiManager
    return wifiManager.dhcpInfo
}
