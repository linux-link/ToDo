package com.wujia.ui.tabs.top;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wujia.ui.R;
import com.wujia.ui.tabs.ISmartTabItem;

public class SmartTabItem extends FrameLayout implements ISmartTabItem<SmartTabInfo<?>> {

    private SmartTabInfo<?> mTabInfo;
    private final ImageView mTabImgView;
    private final TextView mTabNameView;
    private final View mIndicator;

    public SmartTabItem(@NonNull Context context) {
        this(context, null);
    }

    public SmartTabItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartTabItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.smart_tab_item_layout, this);
        mTabImgView = findViewById(R.id.iv_image);
        mTabNameView = findViewById(R.id.tv_name);
        mIndicator = findViewById(R.id.tab_top_indicator);
    }

    @Override
    public void setTabInfo(@NonNull SmartTabInfo<?> smartTabInfo) {
        mTabInfo = smartTabInfo;
        inflateInfo(false, true);
    }

    @Override
    public void resetHeight(int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
        getTabNameView().setVisibility(GONE);
    }

    @Override
    public void onTabSelectedChange(int index, @Nullable SmartTabInfo<?> prevInfo, @NonNull SmartTabInfo<?> nextInfo) {
        if (prevInfo != mTabInfo && nextInfo != mTabInfo || prevInfo == nextInfo) {
            return;
        }
        inflateInfo(prevInfo != mTabInfo, false);
    }

    @ColorInt
    private int getTextColor(Object color) {
        if (color instanceof String) {
            return Color.parseColor((String) color);
        } else {
            return (int) color;
        }
    }

    private void inflateInfo(boolean selected, boolean init) {
        if (mTabInfo.tabType ==SmartTabInfo.TabType.TEXT){
            if (init){
                mTabNameView.setVisibility(VISIBLE);
                mTabImgView.setVisibility(GONE);
                if (!TextUtils.isEmpty(mTabInfo.name)){
                    mTabNameView.setText(mTabInfo.name);
                }
            }
            if (selected) {
                mIndicator.setVisibility(VISIBLE);
                mTabNameView.setTextColor(getTextColor(mTabInfo.tintColor));
            } else {
                mIndicator.setVisibility(GONE);
                mTabNameView.setTextColor(getTextColor(mTabInfo.defaultColor));
            }
            
        }else if (mTabInfo.tabType == SmartTabInfo.TabType.BITMAP){
            if (init) {
                mTabImgView.setVisibility(VISIBLE);
                mTabNameView.setVisibility(GONE);
            }
            if (selected) {
                mIndicator.setVisibility(VISIBLE);
                mTabImgView.setImageBitmap(mTabInfo.selectedBitmap);
            } else {
                mIndicator.setVisibility(GONE);
                mTabImgView.setImageBitmap(mTabInfo.defaultBitmap);
            }
        }
    }

    public SmartTabInfo<?> getTabInfo() {
        return mTabInfo;
    }

    public ImageView getTabImgView() {
        return mTabImgView;
    }

    public TextView getTabNameView() {
        return mTabNameView;
    }
}
