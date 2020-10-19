package com.wujia.resource.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.wujia.resource.R;

/**
 * 翻页显示组件
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/10/18
 */
public class TabDigit extends View implements Runnable {

    private char[] mChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private int mTextSize;
    private int mPaging;
    private int mCornerSize;
    private int mTextColor;
    private int mBackgroundColor;

    // paint
    private Paint mNumberPaint, mDividerPaint, mBackgroundPaint;

    public TabDigit(Context context) {
        super(context, null);
    }

    public TabDigit(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaints();
        initView(context, attrs);
    }

    private void initPaints() {
        mNumberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumberPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mNumberPaint.setColor(Color.WHITE);

        mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDividerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mDividerPaint.setColor(Color.WHITE);
        mDividerPaint.setStrokeWidth(1);

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(Color.BLACK);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabDigit, 0, 0);
//        mTextSize = array.getDimensionPixelSize(R.styleable.TabDigit_textSize, -1);
//        mTextColor = array.getDimensionPixelSize(R.styleable.TabDigit_textColor, -1);
//        mCornerSize = array.getDimensionPixelSize(R.styleable.TabDigit_cornerSize, -1);
//        mBackgroundColor = array.getDimensionPixelSize(R.styleable.TabDigit_backgroundColor, -1);
        array.recycle();
        initTabs();
    }

    private void initTabs() {

    }

    @Override
    public void run() {

    }

    public static class Tab {

    }
}
