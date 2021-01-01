package com.wujia.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.wujia.ui.R;
import com.wujia.ui.utils.DensityUtils;

import java.util.Calendar;
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
public class FlipClockView extends LinearLayout {

    private final int[] mDigitViews = new int[]{R.id.hour_high, R.id.hour_low, R.id.min_high, R.id.min_low,
            R.id.sec_high, R.id.sec_low};

    private int mTextSize;
    private int mCornerSize;
    private int mTextColor;
    private int mBoardColor;
    private int mDividerSize;
    private int mDividerColor;

    private View mRootView;

    /********************************* init function. *********************************************/

    public FlipClockView(Context context) {
        super(context, null);
    }

    public FlipClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.layout_flip_clock, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlipClockView, 0, 0);
        mTextSize = array.getDimensionPixelSize(R.styleable.FlipClockView_digit_textSize, DensityUtils.sp2px(20));
        mTextColor = array.getColor(R.styleable.FlipClockView_digit_textColor, Color.BLACK);
        mCornerSize = array.getDimensionPixelSize(R.styleable.FlipClockView_digit_cornersSize, DensityUtils.dp2px(5));
        mDividerSize = array.getDimensionPixelSize(R.styleable.FlipClockView_digit_dividerSize, DensityUtils.dp2px(1));
        mDividerColor = array.getColor(R.styleable.FlipClockView_digit_dividerColor, Color.BLACK);
        mBoardColor = array.getColor(R.styleable.FlipClockView_digit_boardColor, Color.WHITE);
        array.recycle();
        applyAttribute();
        initTime();
        invalidate();
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
            digitView.setDividerColor(mDividerColor);
        }
    }

    /********************************* inner function end. ****************************************/

    /********************************* expose function. *******************************************/

    private void autoUpdate() {
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

    public void updateTime(int hour,int minute,int second){

        int secLow = second % 10;
        int secHigh = second / 10;

        int minLow = minute % 10;
        int minHigh = minute / 10;

        int hourLow = hour % 10;
        int hourHigh = hour / 10;

        post(() -> {
            //设定秒钟
            getDigitView(mDigitViews[5]).start(secLow);
            if (secLow == 0) {
                getDigitView(mDigitViews[4]).start(secHigh);
            }
            // 设定分钟
            if (second == 0) {
                getDigitView(mDigitViews[3]).start(minLow);
                if (minLow == 0) {
                    getDigitView(mDigitViews[2]).start(minHigh);
                }
            }
            //设定小时
            if (second == 0 && minute == 0) {
                getDigitView(mDigitViews[1]).start(hourLow);
                getDigitView(mDigitViews[0]).start(hourHigh);
            }
        });

    }

    /********************************* expose function end. ***************************************/
}
