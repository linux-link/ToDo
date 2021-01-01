package com.wujia.ui.tabs.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.wujia.ui.R;
import com.wujia.ui.tabs.ISmartTabLayout;
import com.wujia.ui.utils.DensityUtils;
import com.wujia.ui.utils.HiViewUtil;
import com.wujia.ui.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SmartNavigationLayout extends FrameLayout implements ISmartTabLayout<SmartNavigationItem,SmartNavigationInfo<?>> {
    private final List<OnTabSelectedListener<SmartNavigationInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private SmartNavigationInfo<?> selectedInfo;
    private float bottomAlpha = 1f;
    //TabBottom高度
    private static float tabBottomHeight = 50;
    //TabBottom的头部线条高度
    private float bottomLineHeight = 0.5f;
    //TabBottom的头部线条颜色
    private String bottomLineColor = "#dfe0e1";
    private List<SmartNavigationInfo<?>> infoList;

    public SmartNavigationLayout(@NonNull Context context) {
        this(context, null);
    }

    public SmartNavigationLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartNavigationLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void inflateInfo(@NonNull List<SmartNavigationInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        // 移除之前已经添加的View
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }
        selectedInfo = null;
        addBackground();
        //清除之前添加的SmartNavigationItem listener，Tips：Java foreach remove问题
        Iterator<OnTabSelectedListener<SmartNavigationInfo<?>>> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof SmartNavigationItem) {
                iterator.remove();
            }
        }
        int height = DensityUtils.dp2px(tabBottomHeight);
        FrameLayout ll = new FrameLayout(getContext());
        int width = ScreenUtils.getDisplayWidthInPx(getContext()) / infoList.size();
        ll.setTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < infoList.size(); i++) {
            final SmartNavigationInfo<?> info = infoList.get(i);
            //Tips：为何不用LinearLayout：当动态改变child大小后Gravity.BOTTOM会失效
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            params.leftMargin = i * width;

            SmartNavigationItem tabBottom = new SmartNavigationItem(getContext());
            tabSelectedChangeListeners.add(tabBottom);
            tabBottom.setTabInfo(info);
            ll.addView(tabBottom, params);
            tabBottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });
        }
        FrameLayout.LayoutParams flPrams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        flPrams.gravity = Gravity.BOTTOM;
        addBottomLine();
        addView(ll, flPrams);

        fixContentView();
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<SmartNavigationInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    public void setTabAlpha(float alpha) {
        this.bottomAlpha = alpha;
    }

    public void setTabHeight(float tabHeight) {
        this.tabBottomHeight = tabHeight;
    }

    public void setBottomLineHeight(float bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public void setBottomLineColor(String bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    @Nullable
    @Override
    public SmartNavigationItem findTab(@NonNull SmartNavigationInfo<?> info) {
        ViewGroup ll = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof SmartNavigationItem) {
                SmartNavigationItem tab = (SmartNavigationItem) child;
                if (tab.getHiTabInfo() == info) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void defaultSelected(@NonNull SmartNavigationInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    private void onSelected(@NonNull SmartNavigationInfo<?> nextInfo) {
        for (OnTabSelectedListener<SmartNavigationInfo<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }


    private void addBottomLine() {
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));

        FrameLayout.LayoutParams bottomLineParams =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(bottomLineHeight));
        bottomLineParams.gravity = Gravity.BOTTOM;
        bottomLineParams.bottomMargin = DensityUtils.dp2px(tabBottomHeight - bottomLineHeight);
        addView(bottomLine, bottomLineParams);
        bottomLine.setAlpha(bottomAlpha);
    }

    private void addBackground() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.smart_navigation_layout_bg, null);
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(tabBottomHeight));
        params.gravity = Gravity.BOTTOM;
        addView(view, params);
        view.setAlpha(bottomAlpha);
    }

    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";

    /**
     * 修复内容区域的底部Padding
     */
    private void fixContentView() {
        if (!(getChildAt(0) instanceof ViewGroup)) {
            return;
        }
        ViewGroup rootView = (ViewGroup) getChildAt(0);
        ViewGroup targetView = HiViewUtil.findTypeView(rootView, RecyclerView.class);
        if (targetView == null) {
            targetView = HiViewUtil.findTypeView(rootView, ScrollView.class);
        }
        if (targetView == null) {
            targetView = HiViewUtil.findTypeView(rootView, AbsListView.class);
        }
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, DensityUtils.dp2px(tabBottomHeight));
            targetView.setClipToPadding(false);
        }
    }

    public static void clipBottomPadding(ViewGroup targetView) {
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, DensityUtils.dp2px(tabBottomHeight));
            targetView.setClipToPadding(false);
        }
    }

    public void resizeSmartNavigationLayout() {
        int width = ScreenUtils.getDisplayWidthInPx(getContext()) / infoList.size();
        ViewGroup frameLayout = (ViewGroup) getChildAt(getChildCount() - 1);
        int childCount = frameLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View button = frameLayout.getChildAt(i);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) button.getLayoutParams();
            params.width = width;
            params.leftMargin = i * width;
            button.setLayoutParams(params);
        }
    }
}