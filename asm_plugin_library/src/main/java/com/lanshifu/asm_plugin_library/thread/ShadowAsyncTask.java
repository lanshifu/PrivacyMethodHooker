package com.lanshifu.asm_plugin_library.thread;

import android.util.Log;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author neighbWang
 */
public class ShadowAsyncTask {

    /**
     * Optimize the thread pool executor of AsyncTask with {@code allowCoreThreadTimeOut = true}
     */
    public static void optimizeAsyncTaskExecutor() {
        try {
            final ThreadPoolExecutor executor = ((ThreadPoolExecutor) android.os.AsyncTask.THREAD_POOL_EXECUTOR);
            executor.allowCoreThreadTimeOut(true);
            Log.i(Constants.TAG, "Optimize AsyncTask executor success！");
        } catch (final Throwable t) {
            Log.e(Constants.TAG, "Optimize AsyncTask executor error: allowCoreThreadTimeOut = true", t);
        }
    }
}