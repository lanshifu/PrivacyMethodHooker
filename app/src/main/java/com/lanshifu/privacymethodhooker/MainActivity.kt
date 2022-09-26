package com.lanshifu.privacymethodhooker

import android.Manifest
import android.os.Build
import android.os.Bundle
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

        // 代理接口定义：PrivacyMethodManagerDelegate
        PrivacyMethodManager.init(this, object : DefaultPrivacyMethodManagerDelegate() {
            override fun isAgreePrivacy(): Boolean {
                // 是否同意隐私协议
                return isAgreePrivacy
            }

            override fun isUseCache(methodName: String): Boolean {
                //自定义是否要使用缓存，methodName可以在日志找到，过滤一下onPrivacyMethodCall关键字
                if (methodName == "getLine1Number") {
                    return true
                }

                return isUseCache
            }

            override fun isShowPrivacyMethodStack(): Boolean {
                return showPrivacyMethodStack
            }

            override fun onPrivacyMethodCallIllegal(
                className: String,
                methodName: String,
                methodStack: String
            ) {
                super.onPrivacyMethodCallIllegal(className, methodName, methodStack)
            }

            override fun customCacheExpireMap(): HashMap<String, Int> {
                return super.customCacheExpireMap()
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
        getAddress.text = "getAddress=${BlueToothUtil.getBluetoothAdapterAddress(this)}"
        getName.text = "getAddress=${BlueToothUtil.getBluetoothAdapterName(this)}"

        //Runtime.exec
        getSerialNo.text = "getSerialNo=${RuntimeUtil.getSerialNo()}"
        getEth0.text = "getEth0=${RuntimeUtil.getEth0()}"
        getPackage.text = "getPackage=${RuntimeUtil.getPackage()}"

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