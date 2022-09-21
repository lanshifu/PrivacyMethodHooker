package com.lanshifu.privacy_method_hook_library.system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lanshifu.privacy_method_hook_library.log.LogUtil;

import java.io.File;
import java.net.URI;

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
public class HookFile extends File {
    public HookFile(@NonNull String pathname, String callClassName) {
        super(pathname);
        LogUtil.INSTANCE.i("HookFile:pathname=$pathname");
    }

    public HookFile(@Nullable String parent, @NonNull String child, String callClassName) {
        super(parent, child);
    }

    public HookFile(@Nullable File parent, @NonNull String child, String callClassName) {
        super(parent, child);
    }

    public HookFile(@NonNull URI uri, String callClassName) {
        super(uri);
    }
}
