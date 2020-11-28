package com.wujia.resource.dialog;

import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Configure MaterialDialog,see {@link MaterialDialogFragment}.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/9/10
 */
public class MaterialDialogConfig {

    private int mDialogWidth;
    private int mDialogHeight;
    private boolean mCancelable = false;
    private String mCancelText;
    private int mCancelTextColor;
    private String mConfirmText;
    private int mConfirmTextColor;
    private String mTitleText;
    private int mTitleTextColor;
    private String mContentText;
    private int mContentTextColor;
    private boolean mShowCancelBtn = true;
    private boolean mShowConfirmBtn = true;
    private Object mExtras = null;

    private IClickListener mIClickListener;
    private final List<IDismissListener> mIDismissListener = new CopyOnWriteArrayList<>();

    private MaterialDialogConfig() {
    }

    public String getContentText() {
        return mContentText;
    }

    public int getDialogWidth() {
        return mDialogWidth;
    }

    public int getDialogHeight() {
        return mDialogHeight;
    }

    public boolean isCancelable() {
        return mCancelable;
    }

    public boolean isShowConfirmBtn() {
        return mShowConfirmBtn;
    }

    public boolean isShowCancelBtn() {
        return mShowCancelBtn;
    }

    public String getConfirmText() {
        return mConfirmText;
    }

    public String getCancelText() {
        return mCancelText;
    }

    public int getCancelTextColor() {
        return mCancelTextColor;
    }

    public int getConfirmTextColor() {
        return mConfirmTextColor;
    }

    public String getTitleText() {
        return mTitleText;
    }

    public int getTitleTextColor() {
        return mTitleTextColor;
    }

    public int getContentTextColor() {
        return mContentTextColor;
    }

    public Object getExtras() {
        return mExtras;
    }

    public List<IDismissListener> getDismissListener() {
        return mIDismissListener;
    }

    public IClickListener getClickListener() {
        return mIClickListener;
    }

    /**
     * Show dialog in activity.
     *
     * @param activity FragmentActivity
     * @param tag      DialogFragment tag
     */
    public MaterialDialogFragment showDialog(FragmentActivity activity, String tag) {
        MaterialDialogFragment dialogFragment = MaterialDialogFragment.newInstance(this);
        dialogFragment.show(activity.getSupportFragmentManager(), tag);
        return dialogFragment;
    }

    /**
     * Show dialog in fragment.
     *
     * @param fragment Fragment
     * @param tag      DialogFragment tag
     */
    public MaterialDialogFragment showDialog(Fragment fragment, String tag) {
        MaterialDialogFragment dialogFragment = MaterialDialogFragment.newInstance(this);
        dialogFragment.show(fragment.getChildFragmentManager(), tag);
        return dialogFragment;
    }

    /**
     * Dialog configuration builder.
     */
    public static class Builder implements Serializable {

        private final MaterialDialogConfig mConfig = new MaterialDialogConfig();

        public Builder setOnClickListener(IClickListener clickListener) {
            mConfig.mIClickListener = clickListener;
            return this;
        }

        public Builder addOnDismissListener(IDismissListener dismissListener) {
            mConfig.mIDismissListener.add(dismissListener);
            return this;
        }

        public Builder setWidth(int dialogWidth) {
            mConfig.mDialogWidth = dialogWidth;
            return this;
        }

        public Builder setHeight(int dialogHeight) {
            mConfig.mDialogHeight = dialogHeight;
            return this;
        }

        public Builder setContentText(String content) {
            mConfig.mContentText = content;
            return this;
        }

        public Builder setContentTextColor(@ColorInt int color){
            mConfig.mContentTextColor = color;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mConfig.mCancelable = cancelable;
            return this;
        }

        public Builder showConfirmBtn(boolean show) {
            mConfig.mShowConfirmBtn = show;
            return this;
        }

        public Builder setConfirmText(String text) {
            mConfig.mConfirmText = text;
            return this;
        }

        public Builder setConfirmTextColor(@ColorInt int color) {
            mConfig.mConfirmTextColor = color;
            return this;
        }

        public Builder setCancelText(String text) {
            mConfig.mCancelText = text;
            return this;
        }

        public Builder setCancelTextColor(@ColorInt int color) {
            mConfig.mCancelTextColor = color;
            return this;
        }

        public Builder showCancelButton(boolean show) {
            mConfig.mShowCancelBtn = show;
            return this;
        }

        public Builder setTitleText(String text) {
            mConfig.mTitleText = text;
            return this;
        }

        public Builder setTitleTextColor(@ColorInt int color){
            mConfig.mTitleTextColor = color;
            return this;
        }

        public Builder showConfirmButton(boolean show) {
            mConfig.mShowConfirmBtn = show;
            return this;
        }

        public Builder putExtras(Object object) {
            mConfig.mExtras = object;
            return this;
        }

        public MaterialDialogConfig build() {
            return mConfig;
        }

    }

    public interface IClickListener {

        /**
         * Call back when confirm button clicked.
         *
         * @param confirmView Confirm Button.
         * @param dialog      {@link MaterialDialogFragment}
         */
        void onConfirmClickListener(View confirmView,
                                    MaterialDialogFragment dialog);

        /**
         * Call back when cancel button clicked.
         *
         * @param cancelView Cancel Button.
         * @param dialog     {@link MaterialDialogFragment}
         */
        void onCancelClickListener(View cancelView, MaterialDialogFragment dialog);
    }

    public interface IDismissListener {

        /**
         * Call back when dialog dismiss.
         *
         * @param dialog {@link MaterialDialogFragment}
         */
        void onDismiss(MaterialDialogFragment dialog);
    }

}
