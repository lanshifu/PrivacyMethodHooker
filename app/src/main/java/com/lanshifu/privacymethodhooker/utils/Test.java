package com.lanshifu.privacymethodhooker.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * @author lanxiaobin
 * @date 2021/10/9
 */
class Test {

    void test0(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //INVOKEVIRTUAL android/app/ActivityManager.getRunningAppProcesses ()Ljava/util/List;
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {

        }
    }

    void test() {
        ActivityManager activityManager = null;

        //INVOKESTATIC com/lanshifu/privacymethodhooker/utils/PrivacyUtilJava.getRunningAppProcesses (Landroid/app/ActivityManager;)Ljava/util/List;

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : PrivacyUtilJava.getRunningAppProcesses(activityManager)) {

        }
    }
}
