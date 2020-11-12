package com.wujia.arch.utils.permission;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * LiveData fragment.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/11/12
 */
public class LiveFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    private MutableLiveData<PermissionResult> mLiveData;
    private PermissionResult mResult;

    /**
     * Create LiveFragment.
     *
     * @return LiveFragment
     */
    public static LiveFragment newInstance() {
        Bundle args = new Bundle();
        LiveFragment fragment = new LiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * Request Permissions.
     *
     * @param permissions permissions.
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(String[] permissions) {
        mLiveData = new MutableLiveData<>();
        mResult = new PermissionResult();
        List<String> tempPermission = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                tempPermission.add(permission);
            }
        }
        if (tempPermission.isEmpty()) {
            mResult.setAllSuccess(true);
            mLiveData.postValue(mResult);
        } else {
            int size = tempPermission.size();
            String[] strings = tempPermission.toArray(new String[size]);
            requestPermissions(strings, PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            List<String> denyPermission = new ArrayList<>();
            List<String> rationalePermission = new ArrayList<>();

            for (int index = 0; index < grantResults.length; index++) {
                if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                    if (shouldShowRequestPermissionRationale(permissions[index])) {
                        rationalePermission.add(permissions[index]);
                    } else {
                        denyPermission.add(permissions[index]);
                    }
                }
            }
            if (denyPermission.isEmpty() && rationalePermission.isEmpty()) {
                mResult.setAllSuccess(true);
                mLiveData.postValue(mResult);
            } else {
                if (rationalePermission.size() != 0) {
                    int size = rationalePermission.size();
                    mResult.setDenyPermission(rationalePermission.toArray(new String[size]));
                    mLiveData.postValue(mResult);
                } else {
                    int size = denyPermission.size();
                    mResult.setDenyPermission(denyPermission.toArray(new String[size]));
                    mLiveData.postValue(mResult);
                }
            }
        }
    }

    public MutableLiveData<PermissionResult> getLiveData() {
        return mLiveData;
    }
}
