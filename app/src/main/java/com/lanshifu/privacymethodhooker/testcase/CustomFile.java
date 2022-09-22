package com.lanshifu.privacymethodhooker.testcase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.net.URI;

/**
 * @author lanxiaobin
 * @date 2022/9/22
 */
public class CustomFile extends File {

    public CustomFile(@NonNull String pathname) {
        super(pathname);
    }

    public CustomFile(@Nullable String parent, @NonNull String child) {
        super(parent, child);
    }

    public CustomFile(@Nullable File parent, @NonNull String child) {
        super(parent, child);
    }

    public CustomFile(@NonNull URI uri) {
        super(uri);
    }
}
