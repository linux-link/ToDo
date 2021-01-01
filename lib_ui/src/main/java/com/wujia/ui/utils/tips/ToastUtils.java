package com.wujia.ui.utils.tips;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast util.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/10/19
 */
public class ToastUtils {

    private Toast mToast;
    private final Context mContext;

    public ToastUtils(Context context) {
        this.mContext = context;
    }

    /********************** 非连续弹出的Toast ***********************/
    public void showSingleToast(int resId) { //R.string.**
        getSingleToast(resId, Toast.LENGTH_SHORT).show();
    }

    public void showSingleToast(String text) {
        getSingleToast(text, Toast.LENGTH_SHORT).show();
    }

    public void showSingleLongToast(int resId) {
        getSingleToast(resId, Toast.LENGTH_LONG).show();
    }

    public void showSingleLongToast(String text) {
        getSingleToast(text, Toast.LENGTH_LONG).show();
    }

    /*********************** 连续弹出的Toast ************************/
    public void showToast(int resId) {
        getToast(resId, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String text) {
        getToast(text, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(int resId) {
        getToast(resId, Toast.LENGTH_LONG).show();
    }

    public void showLongToast(String text) {
        getToast(text, Toast.LENGTH_LONG).show();
    }

    public Toast getSingleToast(int resId, int duration) { // 连续调用不会连续弹出，只是替换文本
        return getSingleToast(mContext.getResources().getText(resId).toString(), duration);
    }

    public Toast getSingleToast(String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, duration);
        } else {
            mToast.setText(text);
        }
        return mToast;
    }

    public Toast getToast(int resId, int duration) { // 连续调用会连续弹出
        return getToast(mContext.getResources().getText(resId).toString(), duration);
    }

    public Toast getToast(String text, int duration) {
        return Toast.makeText(mContext, text, duration);
    }
}
