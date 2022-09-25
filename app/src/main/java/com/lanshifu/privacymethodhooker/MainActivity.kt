package com.lanshifu.privacymethodhooker

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.lanshifu.privacy_method_hook_library.delegate.DefaultPrivacyMethodManagerDelegate
import com.lanshifu.privacymethodhooker.testcase.*
import com.lanshifu.privacy_method_hook_library.PrivacyMethodManager
import com.lanshifu.privacy_method_hook_library.log.LogUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    var isAgreePrivacy = false
    var isUseCache = false
    var showPrivacyMethodStack = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        cbAgree.setOnCheckedChangeListener { compoundButton, b ->
            isAgreePrivacy = b
            updateData()
        }

        cbUseCache.setOnCheckedChangeListener { compoundButton, b ->
            isUseCache = b
            updateData()
        }

        updateData()

        // 代理接口定义：PrivacyMethodManagerDelegate
        PrivacyMethodManager.init(this, object : DefaultPrivacyMethodManagerDelegate() {
            override fun isAgreePrivacy(): Boolean {
                // 是否同意隐私协议
                return isAgreePrivacy
            }

            override fun isUseCache(methodName: String): Boolean {
                //自定义是否要使用缓存，methodName可以在日志找到，过滤一下onPrivacyMethodCall关键字
                if(methodName == "getLine1Number"){
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
        })

        requestPermissions(arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),0)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun updateData() {

        //TelePhonyManager
        getImei.text = "getImei=${TelePhonyManagerTest.getImei()}"
        getDeviceId.text = "getDeviceId=${TelePhonyManagerTest.getDeviceId(this)}"
        getSubscriberId.text = "getSubscriberId=${TelePhonyManagerTest.getSubscriberId()}"
        getSimSerialNumber.text = "getSimSerialNumber=${TelePhonyManagerTest.getSimSerialNumber()}"
        getMeid.text = "getMeid=${TelePhonyManagerTest.getMeid()}"
        getLine1Number.text = "getLine1Number=${TelePhonyManagerTest.getLine1Number()}"
        getCellLocation.text = "getCellLocation=${TelePhonyManagerTest.getCellLocation()}"
        getNeighboringCellInfo.text = "getNeighboringCellInfo=没有这个了"
        getSimOperator.text = "getSimOperator=${TelePhonyManagerTest.getSimOperator()}"
        getSimOperatorName.text = "getSimOperatorName=${TelePhonyManagerTest.getSimOperatorName()}"
        getSimCountryIso.text = "getSimCountryIso=${TelePhonyManagerTest.getSimCountryIso()}"
        getNetworkOperator.text = "getNetworkOperator=${TelePhonyManagerTest.getNetworkOperator()}"
        getNetworkOperatorName.text = "getNetworkOperatorName=${TelePhonyManagerTest.getNetworkOperatorName()}"
        getNetworkCountryIso.text = "getNetworkCountryIso=${TelePhonyManagerTest.getNetworkCountryIso()}"

        //Settings
        getAndroidBySystem.text = "getAndroidBySystem=${SettingsTest.getAndroidBySystem(contentResolver)}"
        getAndroidIdBySecure.text = "getAndroidIdBySecure=${SettingsTest.getAndroidIdBySecure(contentResolver)}"
        getBluetoothAddress.text = "getBluetoothAddress=${SettingsTest.getBluetoothAddress(contentResolver)}"
        getBluetoothName.text = "getBluetoothName=${SettingsTest.getBluetoothName(contentResolver)}"

        //Runtime.exec
        getSerialNo.text = "getSerialNo=${RuntimeTest.getSerialNo()}"
        getEth0.text = "getEth0=${RuntimeTest.getEth0()}"
        getPackage.text = "getPackage=${RuntimeTest.getPackage()}"

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
    }

}