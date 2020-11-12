package com.wujia.arch.utils.permission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;

/**
 * LiveData Permission.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/11/12
 */
public class LivePermission {

    private static final String TAG = LivePermission.class.getSimpleName();

    private static volatile LiveFragment sInstance;

    private LiveFragment getInstance(FragmentManager fragmentManager) {
        if (sInstance == null) {
            synchronized (LivePermission.class) {
                sInstance = (LiveFragment) fragmentManager.findFragmentByTag(TAG);
                if (sInstance == null) {
                    sInstance = LiveFragment.newInstance();
                    fragmentManager.beginTransaction().add(sInstance, TAG).commitNow();
                }
            }
        }
        return sInstance;
    }

    public LivePermission(AppCompatActivity activity) {
        sInstance = getInstance(activity.getSupportFragmentManager());
    }

    public LivePermission(Fragment fragment) {
        sInstance = getInstance(fragment.getChildFragmentManager());
    }

    public MutableLiveData<PermissionResult> request(String... permissions) {
        return this.requestArray(permissions);
    }

    public MutableLiveData<PermissionResult> requestArray(String[] permissions) {
        sInstance.requestPermissions(permissions);
        return sInstance.getLiveData();
    }

}
