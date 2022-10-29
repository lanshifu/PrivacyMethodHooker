package com.lanshifu.privacymethodhooker.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import android.net.wifi.WifiInfo
import android.os.Build
import androidx.core.app.ActivityCompat
import java.lang.reflect.Method

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
object BlueToothUtil {


    @SuppressLint("HardwareIds")
    fun getBluetoothAdapterAddress(context: Activity): String? {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        return  adapter.address
    }

    @SuppressLint("HardwareIds")
    fun getBluetoothAdapterAddressByReflect(context: Activity): String? {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        val clazz = Class.forName("android.bluetooth.BluetoothAdapter")
        val method: Method = clazz.getDeclaredMethod("getAddress")
        method.isAccessible = true
        return method.invoke(adapter) as  String?
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

    fun getBluetoothAdapterNameByReflect(context: Activity): String? {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        val clazz = Class.forName("android.bluetooth.BluetoothAdapter")
        val method: Method = clazz.getDeclaredMethod("getName")
        method.isAccessible = true
        return method.invoke(adapter) as  String?
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

    @SuppressLint("MissingPermission")
    fun getBluetoothDeviceNameByReflect(context: Activity): String? {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        val bondedDevices = adapter.bondedDevices
        for (bondedDevice in bondedDevices) {
            val clazz = Class.forName("android.bluetooth.BluetoothDevice")
            val method: Method = clazz.getDeclaredMethod("getName")
            method.isAccessible = true
            return method.invoke(bondedDevice) as  String?
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

    @SuppressLint("MissingPermission")
    fun getBluetoothDeviceAddressByReflect(context: Activity): String? {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        val bondedDevices = adapter.bondedDevices
        for (bondedDevice in bondedDevices) {
            val clazz = Class.forName("android.bluetooth.BluetoothDevice")
            val method: Method = clazz.getDeclaredMethod("getAddress")
            method.isAccessible = true
            return method.invoke(bondedDevice) as  String?
        }
        return null
    }

}