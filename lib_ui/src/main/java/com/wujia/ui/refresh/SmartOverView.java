package com.wujia.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wujia.ui.utils.DensityUtils;

public abstract class SmartOverView extends FrameLayout {

    private SmartRefreshState mState = SmartRefreshState.STATE_INIT;

    private int mPullRefreshHeight;
    private float mMinDamp = 1.6f;
    private float mMaxDamp = 2.2f;

    public SmartOverView(@NonNull Context context) {
        this(context, null);
    }

    public SmartOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPullRefreshHeight = DensityUtils.dp2px(66);
        initView();
    }

    protected abstract void initView();

    /**
     * 显示Overlay
     */
    protected abstract void onVisible();

    /**
     * 超过Overlay，释放就会加载
     */
    public abstract void onOver();

    /**
     * 开始加载
     */
    public abstract void onRefresh();

    /**
     * 加载完成
     */
    public abstract void onFinish();

    /**
     * 设置状态
     *
     * @param state 状态
     */
    public void setState(SmartRefreshState state) {
        mState = state;
    }

    /**
     * 获取状态
     *
     * @return 状态
     */
    public SmartRefreshState getState() {
        return mState;
    }
}
