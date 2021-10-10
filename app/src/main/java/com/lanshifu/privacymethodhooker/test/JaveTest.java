package com.lanshifu.privacymethodhooker.test;

import android.content.ContentResolver;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import java.util.List;

/**
 * @author lanxiaobin
 * @date 2021/10/10
 */
class JaveTest {

    String getDeviceId(Context context) {
        ContentResolver cr = context.getContentResolver();
        String androidId = Settings.System.getString(cr, Settings.Secure.ANDROID_ID);
        return androidId;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    List<Sensor> test(Context context) {
        {
            SensorManager manager =
                    (SensorManager) context.getSystemService(SensorManager.class);

            return manager.getSensorList(Sensor.TYPE_ALL);
        }
    }
}
