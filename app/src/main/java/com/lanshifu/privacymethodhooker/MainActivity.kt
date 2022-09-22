package com.lanshifu.privacymethodhooker

import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
    var showPrivacyMethodStack = true

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


        PrivacyMethodManager.setDelegate(object : DefaultPrivacyMethodManagerDelegate(){
            override fun isAgreePrivacy(): Boolean {
                // 是否同意隐私协议
                return isAgreePrivacy
            }

            override fun isUseCache(methodName:String): Boolean {
                return isUseCache
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun updateData() {
        val getRunningAppProcesses = getRunningAppProcesses(this)
        btnGetRunningAppProcesses.text =
            "getRunningAppProcesses size=${getRunningAppProcesses?.size}"

        val getRecentTasks = getRecentTasks(this)
        btnGetRecentTasks.text = ("getRecentTasks size=${getRecentTasks?.size}")

        val getRunningTasks = getRunningTasks(this)
        btnGetRunningTasks.text = ("getRunningTasks size=${getRunningTasks?.size}")

        val getAllCellInfo = getAllCellInfo(this)
        btnGetAllCellInfo.text = ("getAllCellInfo size=${getAllCellInfo?.size}")

        val getDeviceId = getDeviceId(this)
        btnGetDeviceId.text = ("getDeviceId=$getDeviceId")

        val getAndroidId = getAndroidId(this)
        btnGetAndroidId.text = ("getAndroidId=$getAndroidId")
        val getAndroidId2 = getAndroidId2(this)
        btnGetIdAndroid.text = ("androidId2=$getAndroidId2")

        getSimSerialNumber.text = ("getSimSerialNumber=${getSimSerialNumber(this)}")


        getSSID.text = ("getSSID=${getSSID(this)}")
        getBSSID.text = ("getBSSID=${getBSSID(this)}")
        getMacAddress.text = ("getMacAddress=${getMacAddress(this)}")
        getConfiguredNetworks.text =
            ("getConfiguredNetworks,size=${getConfiguredNetworks(this)?.size}")

        getSensorList.text = ("getSensorList size=${getSensorList(this)?.size}")
        getImei.text = ("getImei=${getImei(this)}")

        getScanResults.text = "getScanResults size=${getScanResults(this)?.size}"
        getDhcpInfo.text = "getDhcpInfo=${getDhcpInfo(this)}"

        getLastKnownLocation.text = "getLastKnownLocation=${getLastKnownLocation(this)}"

        requestLocationUpdates(this)
        requestLocationUpdates.text = "requestLocationUpdates"

        /**
         *     NEW java/io/File
        DUP
        LDC ""
        INVOKESPECIAL java/io/File.<init> (Ljava/lang/String;)V
        ASTORE 8
         */
        val file1 = CustomFile("/sys/class/net/wlan0/address")
        val file2 = File("/system/build.prop")
        LogUtil.d(file1.absolutePath + ",length:${file1.length()}")
        LogUtil.d(file2.absolutePath + ",length:${file2.length()}")
    }

}