package com.wujia.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wujia.ui.R;

public class SmartTextOverView extends SmartOverView {

    private TextView mTvContent;
    private View mRotateView;

    public SmartTextOverView(@NonNull Context context) {
        super(context);
    }

    public SmartTextOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.smart_refresh_layout, this, true);
        mTvContent = findViewById(R.id.tv_content);
        mRotateView = findViewById(R.id.iv_rotate);
    }

    @Override
    protected void onVisible() {
        mTvContent.setText("下拉刷新");
    }

    @Override
    public void onOver() {
        mTvContent.setText("松开刷新");
    }

    @Override
    public void onRefresh() {
        mTvContent.setText("正在刷新...");
        Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        operatingAnim.setInterpolator(linearInterpolator);
        mRotateView.startAnimation(operatingAnim);
    }

    @Override
    public void onFinish() {
        mRotateView.clearAnimation();
    }
}
