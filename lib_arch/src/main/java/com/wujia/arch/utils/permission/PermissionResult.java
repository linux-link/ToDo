package com.wujia.arch.utils.permission;

/**
 * Permission Result.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/11/12
 */
public class PermissionResult {

    private boolean mAllSuccess;
    private String[] mDenyPermission;
    private String[] mRationalePermission;

    public boolean isAllSuccess() {
        return mAllSuccess;
    }

    public void setAllSuccess(boolean allSuccess) {
        mAllSuccess = allSuccess;
    }

    public String[] getDenyPermission() {
        return mDenyPermission;
    }

    public void setDenyPermission(String[] denyPermission) {
        mDenyPermission = denyPermission;
    }

    public String[] getRationalePermission() {
        return mRationalePermission;
    }

    public void setRationalePermission(String[] rationalePermission) {
        mRationalePermission = rationalePermission;
    }
}
