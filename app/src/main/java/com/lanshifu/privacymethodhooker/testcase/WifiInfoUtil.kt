package com.lanshifu.privacymethodhooker.testcase

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.DhcpInfo
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
object WifiInfoUtil {


    private fun getWifiInfo(context: Activity): WifiInfo? {

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_WIFI_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
                return null
            }
        }

        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (networkInfo != null && networkInfo.isConnected) {
            val wifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            return wifiManager.connectionInfo
        }
        return null
    }

    fun getIpAddress(context: Activity): Int? {
        return  getWifiInfo(context)?.ipAddress
    }

    fun getSSID(context: Activity): String? {
        return getWifiInfo(context)?.ssid
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
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null
        }
        return wifiManager.scanResults
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getDhcpInfo(context: Activity): DhcpInfo? {
        val wifiManager = context.getSystemService(WifiManager::class.java) as WifiManager
        return wifiManager.dhcpInfo
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getConnectionInfo(context: Activity): WifiInfo? {
        val wifiManager = context.getSystemService(WifiManager::class.java) as WifiManager
        return wifiManager.connectionInfo
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getConfiguredNetworks(context: Activity): MutableList<WifiConfiguration>? {
        val wifiManager = context.getSystemService(WifiManager::class.java) as WifiManager
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            context.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
            return null
        }
        return wifiManager.configuredNetworks
    }

}