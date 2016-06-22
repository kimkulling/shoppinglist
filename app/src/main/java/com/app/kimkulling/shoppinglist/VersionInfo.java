package com.app.kimkulling.shoppinglist;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by kimku_000 on 27.03.2016.
 */
public class VersionInfo {
    public static String version() {
        //try {
            return BuildConfig.VERSION_NAME;
            //return ctx.getPackageManager().getPackageInfo( ctx.getPackageName(),0).versionName;
        //} catch ( PackageManager.NameNotFoundException e ) {
        //    return "Unknown";
        //}
    }
}
