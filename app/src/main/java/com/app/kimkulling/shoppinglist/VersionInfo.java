package com.app.kimkulling.shoppinglist;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by kimku_000 on 27.03.2016.
 */
public class VersionInfo {
    public static String version() {
        return BuildConfig.VERSION_NAME;
    }

    public static int versionCode() {
        return BuildConfig.VERSION_CODE;
    }
}
