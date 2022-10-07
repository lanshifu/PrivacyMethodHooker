package com.lanshifu.privacymethodhooker

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.delegate.DefaultPrivacyMethodManagerDelegate
import com.lanshifu.privacy_method_hook_library.log.LogUtil
import com.lanshifu.privacymethodhooker.testcase.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.lang.reflect.Method

@SuppressLint("Range")
@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity() {

    var isUseCache = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        AlertDialog.Builder(this)
            .setTitle("隐私协议")
            .setMessage("同意隐私协议后才能调用隐私API")
            .setPositiveButton("同意") { p0, p1 ->
                Myapp.isAgreePrivacy = true
                initView()
                updateData()

                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.READ_PHONE_NUMBERS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                    ), 0
                )
            }
            .setNegativeButton("不同意"){p0, p1 ->
               finish()
            }
            .setCancelable(false)
            .show()

        var getCellLocation  = "getCellLocation=${TelePhonyManagerUtil.getCellLocation()}"
    }

    private fun initAfterAgreePrivacy() {
        initView()
        updateData()

        requestPermissions(
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ), 0
        )
    }


    private fun initView(){

        setContentView(R.layout.activity_main)

//        cbAgree.isChecked = true
//        cbAgree.setOnCheckedChangeListener { compoundButton, b ->
//            isAgreePrivacy = b
//            updateData()
//        }

        cbUseCache.setOnCheckedChangeListener { compoundButton, b ->
            isUseCache = b
            updateData()
        }

        refresh.setOnClickListener {
            updateData()
        }


    }

    private fun updateData() {

        //TelePhonyManager
        getImei.text = "getImei[返回null]=${TelePhonyManagerUtil.getImei()}"
        getImei2.text = "getImei2[返回null]=${TelePhonyManagerUtil.getImei2()}"
        getDeviceId.text = "getDeviceId[返回null]=${TelePhonyManagerUtil.getDeviceId(this)}"
        getDeviceId2.text = "getDeviceId[返回null]=${TelePhonyManagerUtil.getDeviceIdWithSlotIndex(this)}"
        getSubscriberId.text = "getSubscriberId[返回null]=${TelePhonyManagerUtil.getSubscriberId()}"
        getSimSerialNumber.text = "getSimSerialNumber[返回null]=${TelePhonyManagerUtil.getSimSerialNumber()}"
        getMeid.text = "getMeid[返回null]=${TelePhonyManagerUtil.getMeid()}"
        getLine1Number.text = "getLine1Number=${TelePhonyManagerUtil.getLine1Number()}"
        getCellLocation.text = "getCellLocation=${TelePhonyManagerUtil.getCellLocation()}"
        getNeighboringCellInfo.text = "getNeighboringCellInfo=没有这个了"
        getSimOperator.text = "getSimOperator=${TelePhonyManagerUtil.getSimOperator()}"
        getSimOperatorName.text = "getSimOperatorName=${TelePhonyManagerUtil.getSimOperatorName()}"
        getSimCountryIso.text = "getSimCountryIso=${TelePhonyManagerUtil.getSimCountryIso()}"
        getNetworkOperator.text = "getNetworkOperator=${TelePhonyManagerUtil.getNetworkOperator()}"
        getNetworkOperatorName.text =
            "getNetworkOperatorName=${TelePhonyManagerUtil.getNetworkOperatorName()}"
        getNetworkCountryIso.text =
            "getNetworkCountryIso=${TelePhonyManagerUtil.getNetworkCountryIso()}"

        //Settings
        getAndroidBySystem.text =
            "getAndroidBySystem=${SettingsUtil.getAndroidBySystem(contentResolver)}"
        getAndroidIdBySecure.text =
            "getAndroidIdBySecure=${SettingsUtil.getAndroidIdBySecure(contentResolver)}"
        getBluetoothAddress.text =
            "getBluetoothAddress=${SettingsUtil.getBluetoothAddress(contentResolver)}"
        getBluetoothName.text = "getBluetoothName=${SettingsUtil.getBluetoothName(contentResolver)}"


        //WifiInfo
        getIpAddress.text = "getIpAddress=${WifiInfoUtil.getIpAddress(this)}"
        getMacAddress.text = "getMacAddress=${WifiInfoUtil.getMacAddress(this)}"
        getSSID.text = "getSSID=${WifiInfoUtil.getSSID(this)}"
        getBSSID.text = "getBSSID=${WifiInfoUtil.getBSSID(this)}"

        //WifiManager
        getScanResults.text = "getScanResults.size=${WifiInfoUtil.getScanResults(this)?.size}"
        getDhcpInfo.text = "getDhcpInfo=${WifiInfoUtil.getDhcpInfo(this)}"
        getConnectionInfo.text = "getConnectionInfo=${WifiInfoUtil.getConnectionInfo(this)}"

        //BluetoothAdapter
        getAddress.text = "getAddress=${BlueToothUtil.getBluetoothAdapterAddress(this)}"
        getName.text = "getName=${BlueToothUtil.getBluetoothAdapterName(this)}"

        //BluetoothAdapter
        bdGetName.text = "getName=${BlueToothUtil.getBluetoothDeviceAddress(this)}"
        bdGetAddress.text = "getAddress=${BlueToothUtil.getBluetoothDeviceName(this)}"

        //Sensor
        getSensorList.text = "getSensorList.size=${SensorUtil.getSensorList(this)?.size}"
        getSensorName.text = "getName=${SensorUtil.getName(this)}"
        getSensorType.text = "getType=${SensorUtil.getType(this)}"
        getSensorVersion.text = "getVersion=${SensorUtil.getVersion(this)}"

        //NetworkInterface
        getHardwareAddress.text =
            "getHardwareAddress=${NetworkInterfaceUtil.getHardwareAddress(this)}"

        //PackageManager
        getInstalledApplications.text =
            "getInstalledApplications size=${PackageManagerUtil.getInstalledApplications(this)?.size}"
        getInstalledPackages.text =
            "getInstalledPackages size=${PackageManagerUtil.getInstalledPackages(this)?.size}"

        //ActivityManager
        getRunningAppProcesses.text =
            "getRunningAppProcesses size=${ActivityManagerUtil.getRunningAppProcesses(this)?.size}"
        getRunningServices.text =
            "getRunningServices size=${ActivityManagerUtil.getRunningServices(this)?.size}"
        getRecentTasks.text =
            "getRecentTasks size=${ActivityManagerUtil.getRecentTasks(this)?.size}"
        getRunningTasks.text =
            "getRunningTasks size=${ActivityManagerUtil.getRunningTasks(this)?.size}"

        //LocationManager
        getLastKnownLocation.text =
            "getLastKnownLocation=${LocationUtil.getLastKnownLocation(this)}"
        getLatitude.text = "getLatitude=${LocationUtil.getLatitude(this)}"
        getLongitude.text = "getLongitude=${LocationUtil.getLongitude(this)}"

        //ContentResolver
        query.text = "query(content://telephony/siminfo) =${query("content://telephony/siminfo")} "

        //getPrimaryClip
        getPrimaryClip.text = "getPrimaryClip=${ClipBoardUtil.paste(this)}"


        //Runtime.exec
        getSerialNo.text = "getprop ro.serialno = ${RuntimeUtil.getSerialNo()}"
        getEth0.text = "cat /sys/class/net/eth0/address = ${RuntimeUtil.getEth0()}"
        getPackage.text = "pm list package = ${RuntimeUtil.getPackage()}"
        ps.text = "ps = ${RuntimeUtil.ps()}"

        //File
        file1.text = "/sys/class/net/etho0/address length=${File("/sys/class/net/etho0/address").length()}"
        file2.text = "/sys/class/net/wlan0/address length=${File("/sys/class/net/wlan0/address").length()}"
        file3.text = "/system/build.prop length=${File("/system/build.prop").length()}"

        try {
            val clazz = Class.forName("android.os.SystemProperties")
            val methodget: Method = clazz.getMethod("get", String::class.java)
            methodget.setAccessible(true)
            val level = methodget.invoke(null, "ro.serialno") as String
            LogUtil.d("serialno= :$level")
        } catch (e: Exception) {
            LogUtil.d("serialno= Exception")
            e.printStackTrace()
        }


    }

    @SuppressLint("Range")
    private fun query(uri: String): String? {
        val cursor =
            contentResolver?.query(Uri.parse(uri), null, null, null, null)
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                val sim_id = cursor.getInt(cursor.getColumnIndex("sim_id"))
                val _id = cursor.getInt(cursor.getColumnIndex("_id"))
                LogUtil.i("siminfo sim_id=$sim_id")
                return "$_id,$sim_id"
            }
        }
        return null
    }

}