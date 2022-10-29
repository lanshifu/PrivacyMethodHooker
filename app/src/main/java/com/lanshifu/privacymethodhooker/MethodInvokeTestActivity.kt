package com.lanshifu.privacymethodhooker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lanshifu.privacymethodhooker.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import lanshifu.utils.SensorUtil

/**
 * @author lanxiaobin
 * @date 2022/10/12
 */
class MethodInvokeTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "反射调用隐私API测试"

        setContentView(R.layout.activity_invoke_test)
        refresh.setOnClickListener {
            updateData()
        }

        updateData()
    }

    @SuppressLint("NewApi", "SetTextI18n")
    fun updateData() {

        //TelePhonyManager
        getImei.text = "getImei[返回null]=${TelePhonyManagerUtil.getImeiByReflect()}"
        getImei2.text = "getImei2[返回null]=${TelePhonyManagerUtil.getImei2ByReflect()}"
        getDeviceId.text = "getDeviceId[返回null]=${TelePhonyManagerUtil.getDeviceIdByReflect(this)}"
        getDeviceId2.text = "getDeviceId[返回null]=${TelePhonyManagerUtil.getDeviceIdWithSlotIndexByReflect(this)}"
        getSubscriberId.text = "getSubscriberId[返回null]=${TelePhonyManagerUtil.getSubscriberIdByReflect()}"
        getSimSerialNumber.text = "getSimSerialNumber[返回null]=${TelePhonyManagerUtil.getSimSerialNumberByReflect()}"
        getMeid.text = "getMeid[返回null]=${TelePhonyManagerUtil.getMeidByReflect()}"
        getLine1Number.text = "getLine1Number=${TelePhonyManagerUtil.getLine1NumberByReflect()}"
        getCellLocation.text = "getCellLocation=${TelePhonyManagerUtil.getCellLocationByReflect()}"
        getNeighboringCellInfo.text = "getNeighboringCellInfo=没有这个了"
        getSimOperator.text = "getSimOperator=${TelePhonyManagerUtil.getSimOperatorByReflect()}"
        getSimOperatorName.text = "getSimOperatorName=${TelePhonyManagerUtil.getSimOperatorNameByReflect()}"
        getSimCountryIso.text = "getSimCountryIso=${TelePhonyManagerUtil.getSimCountryIsoByReflect()}"
        getNetworkOperator.text = "getNetworkOperator=${TelePhonyManagerUtil.getNetworkOperatorByReflect()}"
        getNetworkOperatorName.text =
            "getNetworkOperatorName=${TelePhonyManagerUtil.getNetworkOperatorNameByReflect()}"
        getNetworkCountryIso.text =
            "getNetworkCountryIso=${TelePhonyManagerUtil.getNetworkCountryIsoByReflect()}"

        //Settings
        getAndroidBySystem.text =
            "getAndroidBySystem=${SettingsUtil.getAndroidBySystemByReflect(contentResolver)}"
        getAndroidIdBySecure.text =
            "getAndroidIdBySecure=${SettingsUtil.getAndroidIdBySecureByReflect(contentResolver)}"
        getBluetoothAddress.text =
            "getBluetoothAddress=${SettingsUtil.getBluetoothAddressByReflect(contentResolver)}"
        getBluetoothName.text = "getBluetoothName=${SettingsUtil.getBluetoothNameByReflect(contentResolver)}"

        //WifiInfo
        getIpAddress.text = "getIpAddress=${WifiInfoUtil.getIpAddressByReflect(this)}"
        getMacAddress.text = "getMacAddress=${WifiInfoUtil.getMacAddressByReflect(this)}"
        getSSID.text = "getSSID=${WifiInfoUtil.getSSIDByReflect(this)}"
        getBSSID.text = "getBSSID=${WifiInfoUtil.getBSSIDByReflect(this)}"

        //WifiManager
        getScanResults.text = "getScanResults.size=${WifiInfoUtil.getScanResultsByReflect(this)?.size}"
        getDhcpInfo.text = "getDhcpInfo=${WifiInfoUtil.getDhcpInfoByReflect(this)}"
        getConnectionInfo.text = "getConnectionInfo=${WifiInfoUtil.getConnectionInfoByReflect(this)}"

        //BluetoothAdapter
        getAddress.text = "getAddress=${BlueToothUtil.getBluetoothAdapterAddressByReflect(this)}"
        getName.text = "getName=${BlueToothUtil.getBluetoothAdapterNameByReflect(this)}"

        //BluetoothDevice
        bdGetName.text = "getName=${BlueToothUtil.getBluetoothDeviceAddressByReflect(this)}"
        bdGetAddress.text = "getAddress=${BlueToothUtil.getBluetoothDeviceNameByReflect(this)}"

        //Sensor
        getSensorList.text = "getSensorList.size=${SensorUtil.getSensorListByReflect(this)?.size}"
        getSensorName.text = "getName=${SensorUtil.getNameByReflect(this)}"
        getSensorType.text = "getType=${SensorUtil.getTypeByReflect(this)}"
        getSensorVersion.text = "getVersion=${SensorUtil.getVersionByReflect(this)}"

        //NetworkInterface
//        getHardwareAddress.text =
//            "getHardwareAddress=${NetworkInterfaceUtil.getHardwareAddress(this)}"

        //PackageManager
        getInstalledApplications.text =
            "getInstalledApplications size=${PackageManagerUtil.getInstalledApplicationsByReflect(this)?.size}"
        getInstalledPackages.text =
            "getInstalledPackages size=${PackageManagerUtil.getInstalledPackagesByReflect(this)?.size}"

        //ActivityManager
        getRunningAppProcesses.text =
            "getRunningAppProcesses size=${ActivityManagerUtil.getRunningAppProcessesByReflect(this)?.size}"
        getRunningServices.text =
            "getRunningServices size=${ActivityManagerUtil.getRunningServicesByReflect(this)?.size}"
        getRecentTasks.text =
            "getRecentTasks size=${ActivityManagerUtil.getRecentTasksByReflect(this)?.size}"
        getRunningTasks.text =
            "getRunningTasks size=${ActivityManagerUtil.getRunningTasksByReflect(this)?.size}"

        //LocationManager
        getLastKnownLocation.text =
            "getLastKnownLocation=${LocationUtil.getLastKnownLocationByReflect(this)}"
        getLatitude.text = "getLatitude=${LocationUtil.getLatitudeByReflect(this)}"
        getLongitude.text = "getLongitude=${LocationUtil.getLongitudeByReflect(this)}"
//
//        //ContentResolver
////        query.text = "query(content://telephony/siminfo) =${query("content://telephony/siminfo")} "

        //getPrimaryClip
        getPrimaryClip.text = "getPrimaryClip=${ClipBoardUtil.pasteByReflect(this)}"

        //Runtime.exec
        getSerialNo.text = "getprop ro.serialno = ${RuntimeUtil.execByReflect("getprop ro.serialno")}"
        getEth0.text = "cat /sys/class/net/eth0/address = ${RuntimeUtil.execByReflect("cat /sys/class/net/eth0/address")}"
        getPackage.text = "pm list package = ${RuntimeUtil.execByReflect("pm list package")}"
        ps.text = "ps = ${RuntimeUtil.execByReflect("ps")}"
//
//        //File
//        file1.text = "/sys/class/net/etho0/address length=${File("/sys/class/net/etho0/address").length()}"
//        file2.text = "/sys/class/net/wlan0/address length=${File("/sys/class/net/wlan0/address").length()}"
//        file3.text = "/system/build.prop length=${File("/system/build.prop").length()}"



    }

}