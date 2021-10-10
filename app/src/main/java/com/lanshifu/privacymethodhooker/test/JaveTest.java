package com.lanshifu.privacymethodhooker.test;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

/**
 * @author lanxiaobin
 * @date 2021/10/10
 */
class JaveTest {

    String getDeviceId(Context context){
        ContentResolver cr = context.getContentResolver();
        String androidId = Settings.System.getString(cr, Settings.Secure.ANDROID_ID);
        return androidId;
    }
}
