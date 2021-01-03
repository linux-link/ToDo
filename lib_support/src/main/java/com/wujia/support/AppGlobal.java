package com.wujia.support;

import android.app.Application;
import android.util.Log;

import java.lang.reflect.Method;

public class AppGlobal {

    private AppGlobal() {

    }

    private static final AppGlobal APP_GLOBAL = new AppGlobal();

    public static Application getApplication() {
        Application application = null;
        try {
            Class atClass = Class.forName("android.app.ActivityThread");
            Method currentApplicationMethod = atClass.getDeclaredMethod("currentApplication");
            currentApplicationMethod.setAccessible(true);
            application = (Application) currentApplicationMethod.invoke(null);
            Log.d("fw_create", "curApp class1:" + application);
        } catch (Exception e) {
            Log.d("fw_create", "e:" + e.toString());
        }

        if (application != null)
            return application;

        try {
            Class atClass = Class.forName("android.app.AppGlobals");
            Method currentApplicationMethod = atClass.getDeclaredMethod("getInitialApplication");
            currentApplicationMethod.setAccessible(true);
            application = (Application) currentApplicationMethod.invoke(null);
            Log.d("fw_create", "curApp class2:" + application);
        } catch (Exception e) {
            Log.d("fw_create", "e:" + e.toString());
        }

        return application;
    }

}
