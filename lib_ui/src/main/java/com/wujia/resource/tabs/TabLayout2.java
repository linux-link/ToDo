package com.wujia.resource.tabs;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import com.wujia.resource.R;

/**
 * TabLayout.
 *
 * @author WuJia.
 * @version 1.0
 * @date 11/21/20
 */
public class TabLayout2 extends HorizontalScrollView {

    private int mViewHeight, mViewWidth;

    public TabLayout2(Context context) {
        super(context, null);
    }

    public TabLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabLayout2);
        array.recycle();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }


}
