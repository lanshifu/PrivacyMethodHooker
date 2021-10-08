package com.lanshifu.asm_plugin_library;

import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 检测快速点击
 */
public class FastClickUtil {


    // 快速点击间隔时间，毫秒
    private static long FAST_CLICK_TIME = 1000;

    /**
     * 设置快速点击间隔时间，毫秒
     * @param time
     */
    public static void setFastClickTime(long time) {
        FAST_CLICK_TIME = time;
    }


    private static final String TAG = FastClickUtil.class.getSimpleName();

    private static final Map<View, FrozenView> viewWeakHashMap = new WeakHashMap<>();

    public static boolean canClick(View targetView) {

        FrozenView frozenView = viewWeakHashMap.get(targetView);
        final long now = now();

        if (frozenView == null) {
            frozenView = new FrozenView(targetView);
            frozenView.setFrozenWindow(now + FAST_CLICK_TIME);
            viewWeakHashMap.put(targetView, frozenView);
            return true;
        }

        if (now >= frozenView.getFrozenWindowTime()) {
            frozenView.setFrozenWindow(now + FAST_CLICK_TIME);
            return true;
        }
        Log.d(TAG, "canClick: false,FAST_CLICK_TIME=" + FAST_CLICK_TIME);

        return false;
    }

    private static long now() {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
    }

    private static class FrozenView extends WeakReference<View> {
        private long FrozenWindowTime;

        FrozenView(View referent) {
            super(referent);
        }

        long getFrozenWindowTime() {
            return FrozenWindowTime;
        }

        void setFrozenWindow(long expirationTime) {
            this.FrozenWindowTime = expirationTime;
        }
    }
}

