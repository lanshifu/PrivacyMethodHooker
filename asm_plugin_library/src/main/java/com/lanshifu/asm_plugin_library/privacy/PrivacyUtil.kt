package com.lanshifu.asm_plugin_library.privacy

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.telephony.TelephonyManager
import android.util.Log
import com.lanshifu.asm_plugin_library.privacy.ReflectUtils.invokeMethod
import com.lanshifu.privacy_method_annotation.AsmField
import org.objectweb.asm.Opcodes
import java.util.*

/**
 * @author lanxiaobin
 * @date 2021/9/30.
 */
object PrivacyUtil {

    @SuppressLint("MissingPermission")
    @AsmField(oriClass = TelephonyManager::class, oriAccess = Opcodes.INVOKEVIRTUAL)
    fun getDeviceId(telephonyManager: TelephonyManager): String {
        Log.d("PrivacyUtil", "getDeviceId")
        //        if (!GoldCicada.isCanRequestPrivacyInfo()) {
//            // print or record StackTrace
//            return "";
//        }
        return telephonyManager.deviceId
    }

    @AsmField(oriClass = ActivityManager::class, oriAccess = Opcodes.INVOKEVIRTUAL)
    fun getRunningAppProcesses(activityManager: ActivityManager): List<ActivityManager.RunningAppProcessInfo> {
        Log.d("PrivacyUtil", "getRunningAppProcesses")
        //        if (!GoldCicada.isCanRequestPrivacyInfo()) {
//            // print or record StackTrace
//            return "";
//        }
        return ArrayList()
    }

    /**
     * 因为我们无法知道运行时的子类名称，因此我们也不知道被 hook 的类是谁，
     * 这里就用 Object 对象替代，并且参数也用 Object，在运行时判断传入的参数是否是 Activity 的子类，
     * 如果是就正常调用，不是的话就反射调用。
     * @param activity
     * @param permissions
     * @param requestCode
     */
    @AsmField(oriClass = Any::class, oriAccess = Opcodes.INVOKESPECIAL)
    fun requestPermissions(activity: Any, permissions: Array<String>, requestCode: Int) {
        Log.d("PrivacyUtil", "requestPermissions")
        if (activity is Activity) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                activity.requestPermissions(permissions, requestCode)
//            }
        } else { //非Activity hook，反射调用
            invokeMethod<Any>(
                activity, "requestPermissions", arrayOf(
                    Array<String>::class.java, Integer.TYPE
                ), arrayOf(permissions, requestCode)
            )
        }
    }
}