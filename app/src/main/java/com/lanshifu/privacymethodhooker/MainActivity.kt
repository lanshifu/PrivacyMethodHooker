package com.lanshifu.privacymethodhooker

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.delegate.DefaultPrivacyMethodManagerDelegate
import com.lanshifu.privacy_method_hook_library.log.LogUtil
import com.lanshifu.privacymethodhooker.testcase.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.lang.reflect.Method

class MainActivity : AppCompatActivity() {

    var isAgreePrivacy = false
    var isUseCache = false
    var showPrivacyMethodStack = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PrivacyMethodManager.init(this, object : DefaultPrivacyMethodManagerDelegate() {
            override fun isAgreePrivacy(): Boolean {
                // 是否同意隐私协议
                return isAgreePrivacy
            }

            override fun isUseCache(methodName: String, callerClassName:String): Boolean {
                //自定义是否要使用缓存，methodName可以在日志找到，过滤一下onPrivacyMethodCall关键字
                if (methodName == "getLine1Number") {
                    return true
                }

                // 父类处理黑名单了，这里复用
                return if(super.isUseCache(methodName, callerClassName)){
                    isUseCache
                } else {
                    false
                }

            }

            override fun isShowPrivacyMethodStack(): Boolean {
                return showPrivacyMethodStack
            }

            override fun onPrivacyMethodCallIllegal(
                callerClassName: String,
                methodName: String,
                methodStack: String
            ) {
                // super 有toast，如果需要自己弹窗，可以注释掉
//                super.onPrivacyMethodCallIllegal(className, methodName, methodStack)
                Log.e(
                    "PrivacyMethodManager",
                    "onPrivacyMethodCallIllegal,className=$callerClassName，methodName=$methodName,methodStack=$methodStack"
                )
            }

            override fun customCacheExpireMap(): HashMap<String, Int> {
                return HashMap<String, Int>().apply {
                    ///缓存60s过期
                    this["TelephonyManager#getSimCountryIso"] = 10
                }
            }
        })

        setContentView(R.layout.activity_main)

        cbAgree.setOnCheckedChangeListener { compoundButton, b ->
            isAgreePrivacy = b
            updateData()
        }

        cbUseCache.setOnCheckedChangeListener { compoundButton, b ->
            isUseCache = b
            updateData()
        }

        refresh.setOnClickListener {
            updateData()
        }

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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun updateData() {

        //TelePhonyManager
        getImei.text = "getImei=${TelePhonyManagerUtil.getImei()}"
        getImei2.text = "getImei2=${TelePhonyManagerUtil.getImei2()}"
        getDeviceId.text = "getDeviceId=${TelePhonyManagerUtil.getDeviceId(this)}"
        getDeviceId2.text = "getDeviceId=${TelePhonyManagerUtil.getDeviceIdWithSlotIndex(this)}"
        getSubscriberId.text = "getSubscriberId=${TelePhonyManagerUtil.getSubscriberId()}"
        getSimSerialNumber.text = "getSimSerialNumber=${TelePhonyManagerUtil.getSimSerialNumber()}"
        getMeid.text = "getMeid=${TelePhonyManagerUtil.getMeid()}"
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
        getName.text = "getAddress=${BlueToothUtil.getBluetoothAdapterName(this)}"

        //BluetoothAdapter
        bdGetName.text = "getName=${BlueToothUtil.getBluetoothDeviceAddress(this)}"
        bdGetAddress.text = "getAddress=${BlueToothUtil.getBluetoothDeviceName(this)}"

        //Sensor
        getSensorList.text = "getSensorList.size=${SensorUtil.getSensorList(this)?.size}"
        getSensorName.text = "getName=${SensorUtil.getName(this)}"
        getSensorType.text = "getType=${SensorUtil.getType(this)}"
        getSensorVersion.text = "getVersion=${SensorUtil.getVersion(this)}"

        //NetworkInterface
        getHardwareAddress.text = "getHardwareAddress=${NetworkInterfaceUtil.getHardwareAddress(this)}"

        //PackageManager
        getInstalledApplications.text = "getInstalledApplications size=${PackageManagerUtil.getInstalledApplications(this)?.size}"
        getInstalledPackages.text = "getInstalledPackages size=${PackageManagerUtil.getInstalledPackages(this)?.size}"

        //ActivityManager
        getRunningAppProcesses.text = "getRunningAppProcesses size=${ActivityManagerUtil.getRunningAppProcesses(this)?.size}"
        getRunningServices.text = "getRunningServices size=${ActivityManagerUtil.getRunningServices(this)?.size}"
        getRecentTasks.text = "getRecentTasks size=${ActivityManagerUtil.getRecentTasks(this)?.size}"
        getRunningTasks.text = "getRunningTasks size=${ActivityManagerUtil.getRunningTasks(this)?.size}"

        //LocationManager
        getLastKnownLocation.text = "getLastKnownLocation=${LocationUtil.getLastKnownLocation(this)}"
        getLatitude.text = "getLatitude=${LocationUtil.getLatitude(this)}"
        getLongitude.text = "getLongitude=${LocationUtil.getLongitude(this)}"


        //Runtime.exec
        getSerialNo.text = "getprop ro.serialno = ${RuntimeUtil.getSerialNo()}"
        getEth0.text = "cat /sys/class/net/eth0/address = ${RuntimeUtil.getEth0()}"
        getPackage.text = "pm list package = ${RuntimeUtil.getPackage()}"
        ps.text = "ps = ${RuntimeUtil.ps()}"

        /**
         *     NEW java/io/File
        DUP
        LDC ""
        INVOKESPECIAL java/io/File.<init> (Ljava/lang/String;)V
        ASTORE 8
         */
        val file0 = CustomFile("/sys/class/net/etho0/address")

        val file1 = File("/sys/class/net/etho0/address")
        val file2 = File("/sys/class/net/wlan0/address")
        val file3 = File("/system/build.prop")
        val file4 = File(externalCacheDir?.absolutePath ?: "")
        LogUtil.d(file0.absolutePath + ",length:${file0.length()}")
        LogUtil.d(file2.absolutePath + ",length:${file2.length()}")
        LogUtil.d(file3.absolutePath + ",length:${file3.length()}")
        LogUtil.d(file4.absolutePath + ",length:${file4.length()}")

        try {
            val clazz = Class.forName("android.os.SystemProperties")
            val methodget: Method = clazz.getMethod("get", String::class.java)
            methodget.setAccessible(true)
            val level = methodget.invoke(null as Any?, "ro.serialno") as String
            LogUtil.d("serialno= :$level")
        } catch (e: Exception) {
            LogUtil.d("Exception")
            e.printStackTrace()
        }
    }

}