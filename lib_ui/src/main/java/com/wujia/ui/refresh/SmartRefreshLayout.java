package com.wujia.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SmartRefreshLayout extends FrameLayout implements ISmartRefresh {

    public static final String TAG = SmartRefreshLayout.class.getSimpleName();

    public SmartRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public SmartRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setDisableRefreshOnScroll(boolean disableRefreshScroll) {

    }

    @Override
    public void onRefreshFinished() {

    }

    @Override
    public void setRefreshListener(SmartRefreshListener listener) {

    }

    @Override
    public void setRefreshOverView(SmartOverView overView) {

    }
}
