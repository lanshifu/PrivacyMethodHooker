package com.lanshifu.privacymethodhooker.testcase

import android.app.Activity
import java.net.NetworkInterface

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
object NetworkInterfaceUtil {

    fun getHardwareAddress(context: Activity): ByteArray? {
        val getNetworkInterfaces = NetworkInterface.getNetworkInterfaces()
        if(getNetworkInterfaces != null){
            for (networkInterface in getNetworkInterfaces) {
                return networkInterface.hardwareAddress
            }
        }

        return null
    }

}