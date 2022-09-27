package com.lanshifu.privacymethodhooker.testcase

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import androidx.core.app.ActivityCompat

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
object BlueToothUtil {


    fun getBluetoothAdapterAddress(context: Activity): String? {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        return  adapter.address
    }

    fun getBluetoothAdapterName(context: Activity): String? {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_CONNECT),1)
            }
            return null
        }

        return adapter.name
    }

    fun getBluetoothDeviceName(context: Activity): String? {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_CONNECT),3)
            }
            return null
        }

        val bondedDevices = adapter.bondedDevices
        for (bondedDevice in bondedDevices) {
            return bondedDevice.name
        }
        return null
    }


    fun getBluetoothDeviceAddress(context: Activity): String? {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_CONNECT),3)
            }
            return null
        }

        val bondedDevices = adapter.bondedDevices
        for (bondedDevice in bondedDevices) {
            return bondedDevice.address
        }
        return null
    }

}