package com.wujia.resource.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;

/**
 * 自定义 PopupWindow.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/10/19
 */
public class PPWindow implements PopupWindow.OnDismissListener {

    private static final float DEFAULT_ALPHA = 0.7f;

    private int mWidth, mHeight;
    private boolean mIsFocusable;
    private int mContentLayoutId;
    private View mContentView;
    private boolean mIsOutsideTouchable;
    private int mAnimationStyle;
    private boolean mClippingEnable = true;
    private boolean mIgnoreCheekPress;
    private int mInputMode;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private int mSoftInputMode;
    private boolean mTouchable = true;
    private View.OnTouchListener mOnTouchListener;
    // 弹出PopWindow 背景是否变暗，默认不会变暗。
    private boolean mIsBackgroundDark;
    // 背景变暗的值，0 - 1
    private float mBackgroundDarkValue = 0;
    // 设置是否允许点击 PopupWindow之外的地方，关闭PopupWindow
    private boolean mEnableOutsideTouchDisMiss = true;

    private PopupWindow mPopupWindow;
    private Context mContext;
    private Window mWindow;

    public void initView() {
        if (mContentView == null) {
            mContentView = LayoutInflater.from(mContext).inflate(mContentLayoutId, null);
        }
        Activity activity = (Activity) mContentView.getContext();
        if (activity != null && mIsBackgroundDark) {
            final float alpha = (mBackgroundDarkValue > 0 && mBackgroundDarkValue < 1) ? mBackgroundDarkValue : DEFAULT_ALPHA;
            mWindow = activity.getWindow();
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.alpha = alpha;
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mWindow.setAttributes(params);
        }

        if (mWidth != 0 && mHeight != 0) {
            mPopupWindow = new PopupWindow(mContentView, mWidth, mHeight);
        } else {
            mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if (mAnimationStyle != 0) {
            mPopupWindow.setAnimationStyle(mAnimationStyle);
        }
        initAttribute(mPopupWindow);
        if (mWidth == 0 || mHeight == 0) {
            mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            mWidth = mPopupWindow.getContentView().getMeasuredWidth();
            mHeight = mPopupWindow.getContentView().getMeasuredHeight();
        }

        mPopupWindow.setOnDismissListener(this);

        if (!mEnableOutsideTouchDisMiss) {
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setBackgroundDrawable(null);

            mPopupWindow.getContentView().setFocusable(true);
            mPopupWindow.getContentView().setFocusableInTouchMode(true);
            mPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(final View v, final int keyCode, final KeyEvent event) {
                    if (KeyEvent.KEYCODE_BACK == keyCode) {
                        mPopupWindow.dismiss();
                        return true;
                    }
                    return false;
                }
            });

            mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(final View v, final MotionEvent event) {
                    final int x = (int) event.getX();
                    final int y = (int) event.getY();
                    if ((event.getAction() == MotionEvent.ACTION_DOWN)
                            && ((x < 0) || (x >= mWidth) || (y < 0) || (y >= mHeight))) {
                        return true;
                    } else return event.getAction() == MotionEvent.ACTION_DOWN;
                }
            });
        } else {
            mPopupWindow.setFocusable(mIsFocusable);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopupWindow.setOutsideTouchable(mIsOutsideTouchable);
        }
        mPopupWindow.update();
    }

    private void initAttribute(final PopupWindow popupWindow) {
        popupWindow.setClippingEnabled(mClippingEnable);
        if (mIgnoreCheekPress) {
            popupWindow.setIgnoreCheekPress();
        }
        if (mInputMode != 0) {
            popupWindow.setInputMethodMode(mInputMode);
        }
        if (mSoftInputMode != 0) {
            popupWindow.setSoftInputMode(mSoftInputMode);
        }
        if (mOnDismissListener != null) {
            popupWindow.setOnDismissListener(mOnDismissListener);
        }
        if (mOnTouchListener != null) {
            popupWindow.setTouchInterceptor(mOnTouchListener);
        }
        popupWindow.setTouchable(mTouchable);
    }

    @Override
    public void onDismiss() {
        dismiss();
    }

    /**
     * 相对于父控件的位置（通过设置Gravity.CENTER，下方Gravity.BOTTOM等 ），可以设置具体位置坐标.
     *
     * @param parent  父控件
     * @param gravity 重心
     * @param x       the popup's x location offset
     * @param y       the popup's y location offset
     * @return this
     */
    public PPWindow showAtLocation(View parent, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
        }
        return this;
    }

    public PPWindow showAsDropDown(View anchor) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor);
        }
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PPWindow showAsDropDown(View anchor, int xOff, int yOff, int gravity) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff, gravity);
        }
        return this;
    }

    public PPWindow showAsDropDown(View anchor, int xOff, int yOff) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff);
        }
        return this;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    /**
     * 关闭PopupWindow.
     */
    public void dismiss() {
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss();
        }
        if (mWindow != null) {
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.alpha = 1.0f;
            mWindow.setAttributes(params);
        }
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }

    public static class Builder {

        private final PPWindow mPpWindow = new PPWindow();

        public Builder size(int width, int height) {
            mPpWindow.mHeight = height;
            mPpWindow.mWidth = width;
            return this;
        }

        public Builder focusable(boolean focusable) {
            mPpWindow.mIsFocusable = focusable;
            return this;
        }

        public Builder setView(@LayoutRes int resLayoutId) {
            mPpWindow.mContentLayoutId = resLayoutId;
            mPpWindow.mContentView = null;
            return this;
        }

        public Builder setView(View view) {
            mPpWindow.mContentView = view;
            mPpWindow.mContentLayoutId = -1;
            return this;
        }

        public Builder setOutsideTouchable(boolean touchable) {
            mPpWindow.mIsOutsideTouchable = touchable;
            return this;
        }

        /**
         * 弹窗动画
         *
         * @param animationStyle
         * @return {@link Builder}
         */
        public Builder setAnimationStyle(int animationStyle) {
            mPpWindow.mAnimationStyle = animationStyle;
            return this;
        }

        public Builder setClippingEnable(boolean enable) {
            mPpWindow.mClippingEnable = enable;
            return this;
        }

        public Builder setIgnoreCheekPress(boolean ignoreCheekPress) {
            mPpWindow.mIgnoreCheekPress = ignoreCheekPress;
            return this;
        }

        public Builder setInputMethodMode(int mode) {
            mPpWindow.mInputMode = mode;
            return this;
        }

        public Builder setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
            mPpWindow.mOnDismissListener = onDismissListener;
            return this;
        }


        public Builder setSoftInputMode(int softInputMode) {
            mPpWindow.mSoftInputMode = softInputMode;
            return this;
        }


        public Builder setTouchable(boolean touchable) {
            mPpWindow.mTouchable = touchable;
            return this;
        }

        public Builder setTouchInterceptor(View.OnTouchListener touchInterceptor) {
            mPpWindow.mOnTouchListener = touchInterceptor;
            return this;
        }

        /**
         * 设置背景变暗是否可用
         *
         * @param isDark
         * @return
         */
        public Builder enableBackgroundDark(boolean isDark) {
            mPpWindow.mIsBackgroundDark = isDark;
            return this;
        }

        /**
         * 设置背景变暗的值
         *
         * @param darkValue
         * @return
         */
        public Builder setBgDarkAlpha(float darkValue) {
            mPpWindow.mBackgroundDarkValue = darkValue;
            return this;
        }

        /**
         * 设置是否允许点击 PopupWindow之外的地方，关闭PopupWindow
         *
         * @param disMiss
         * @return
         */
        public Builder enableOutsideTouchableDismiss(boolean disMiss) {
            mPpWindow.mEnableOutsideTouchDisMiss = disMiss;
            return this;
        }

        public PPWindow build(Context context) {
            mPpWindow.mContext = context;
            mPpWindow.initView();
            return mPpWindow;
        }

    }
}
