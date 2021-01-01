package com.wujia.arch;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.wujia.arch.utils.AppLifecycle;
import com.wujia.arch.utils.AppStackManager;

/**
 * Base application.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/9/1
 */
public class AbsApplication extends Application implements LifecycleObserver {

    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        setApplication(this);
    }

    public static Application getApplication() {
        return sApplication;
    }

    private static void setApplication(Application application) {
        sApplication = application;

        ProcessLifecycleOwner.get().getLifecycle().addObserver(AppLifecycle.getInstance());

        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                AppStackManager.INSTANCE.addActivity(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                AppStackManager.INSTANCE.removeActivity(activity);
            }
        });
    }

}
