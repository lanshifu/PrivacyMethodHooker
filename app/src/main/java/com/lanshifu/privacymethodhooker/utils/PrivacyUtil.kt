package com.lanshifu.privacymethodhooker.utils;

import android.app.ActivityManager;

import com.lanshifu.privacy_method_annotation.AsmField;

import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lanxiaobin
 * @date 2021/10/9
 */
public class PrivacyUtilJava {
    @AsmField(oriClass = ActivityManager.class, oriAccess = Opcodes.INVOKEVIRTUAL)
    public static List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses(ActivityManager activityManager) {

        return new ArrayList();
    }

}
