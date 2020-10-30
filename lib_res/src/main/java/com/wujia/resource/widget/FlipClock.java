package com.wujia.resource.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.wujia.resource.R;
import com.wujia.resource.utils.DensityUtils;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 翻页时钟.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/10/17
 */
public class FlipClock extends LinearLayout {

    private final int[] mDigitViews = new int[]{R.id.hour_high, R.id.hour_low, R.id.min_high, R.id.min_low,
            R.id.sec_high, R.id.sec_low};

    private int mTextSize;
    private int mCornerSize;
    private int mTextColor;
    private int mBoardColor;
    private int mDividerSize;

    private View mRootView;

    /********************************* init function. *********************************************/

    public FlipClock(Context context) {
        super(context, null);
    }

    public FlipClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.layout_flip_clock, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlipClock, 0, 0);
        mTextSize = array.getDimensionPixelSize(R.styleable.FlipClock_digit_textSize, DensityUtils.sp2px(20));
        mTextColor = array.getColor(R.styleable.FlipClock_digit_textColor, Color.BLACK);
        mCornerSize = array.getDimensionPixelSize(R.styleable.FlipClock_digit_cornersSize, DensityUtils.dp2px(5));
        mDividerSize = array.getDimensionPixelSize(R.styleable.FlipClock_digit_dividerSize, DensityUtils.dp2px(1));
        mBoardColor = array.getColor(R.styleable.FlipClock_digit_boardColor, Color.WHITE);
        array.recycle();
        applyAttribute();
        initTime();
    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        //设定秒钟
        int sec = calendar.get(Calendar.SECOND);
        int secHigh = sec / 10;
        getDigitView(mDigitViews[4]).start(secHigh);

        int min = calendar.get(Calendar.MINUTE);
        int minLow = min % 10;
        int minHigh = min / 10;
        getDigitView(mDigitViews[3]).start(minLow);
        getDigitView(mDigitViews[2]).start(minHigh);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int hourLow = hour % 10;
        int hourHigh = hour / 10;
        getDigitView(mDigitViews[1]).start(hourLow);
        getDigitView(mDigitViews[0]).start(hourHigh);
        scheduleApplyTime();
    }

    private void scheduleApplyTime() {
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);
        scheduled.scheduleAtFixedRate(() -> {
            Calendar calendar = Calendar.getInstance();
            int sec = calendar.get(Calendar.SECOND);
            int secLow = sec % 10;
            int secHigh = sec / 10;

            int min = calendar.get(Calendar.MINUTE);
            int minLow = min % 10;
            int minHigh = min / 10;

            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int hourLow = hour % 10;
            int hourHigh = hour / 10;

            post(() -> {
                //设定秒钟
                getDigitView(mDigitViews[5]).start(secLow);
                if (secLow == 0) {
                    getDigitView(mDigitViews[4]).start(secHigh);
                }
                // 设定分钟
                if (sec == 0) {
                    getDigitView(mDigitViews[3]).start(minLow);
                    if (minLow == 0) {
                        getDigitView(mDigitViews[2]).start(minHigh);
                    }
                }
                //设定小时
                if (sec == 0 && min == 0) {
                    getDigitView(mDigitViews[1]).start(hourLow);
                    getDigitView(mDigitViews[0]).start(hourHigh);
                }
            });
        }, 0, 1, TimeUnit.SECONDS);
    }

    /********************************* init function end. *****************************************/

    /********************************* inner function. ********************************************/

    private DigitView getDigitView(@IdRes int id) {
        return mRootView.findViewById(id);
    }

    private void applyAttribute() {
        for (int id : mDigitViews) {
            DigitView digitView = getDigitView(id);
            digitView.setBoardColor(mBoardColor);
            digitView.setCornerSize(mCornerSize);
            digitView.setTextColor(mTextColor);
            digitView.setTextSize(mTextSize);
            digitView.setDividerSize(mDividerSize);
            if (getBackground() != null) {
                digitView.setBackground(getBackground());
            }
        }
    }

    /********************************* inner function end. ****************************************/
}
