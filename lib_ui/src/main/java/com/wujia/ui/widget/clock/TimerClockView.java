package com.wujia.ui.widget.clock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.wujia.ui.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerClockView extends View {

    private Paint mScalePaint;
    private Paint mPointerPaint;
    private int mViewHeight, mViewWidth;
    private int mScaleRadius;
    private int mSecondDegree = 0;

    public TimerClockView(Context context) {
        super(context, null);
    }

    public TimerClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimerClockView);
        int pointerColor = array.getColor(R.styleable.TimerClockView_timer_PointColor, Color.RED);
        int scaleColor = array.getColor(R.styleable.TimerClockView_timer_scaleColor, Color.GRAY);
        array.recycle();

        mPointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointerPaint.setStrokeCap(Paint.Cap.ROUND);
        mPointerPaint.setStrokeWidth(35);
        mPointerPaint.setColor(pointerColor);
        mPointerPaint.setShadowLayer(1, 0, 0, pointerColor);

        mScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScalePaint.setStrokeCap(Paint.Cap.ROUND);
        mScalePaint.setColor(scaleColor);
        mScalePaint.setStrokeWidth(5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight = h;
        mViewWidth = w;
        mScaleRadius = mViewWidth / 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScale(canvas);
        drawText(canvas);
        drawPointer(canvas);
    }

    private void drawPointer(Canvas canvas) {
        canvas.save();
        canvas.translate(mViewWidth / 2f, mViewHeight / 2f);
        canvas.rotate(mSecondDegree);
        canvas.drawPoint(0, -mScaleRadius, mPointerPaint);
        canvas.restore();
    }

    private void drawScale(Canvas canvas) {
        canvas.save();
        canvas.translate(mViewWidth / 2f, mViewHeight / 2f);
        for (int i = 0; i < 360; i++) {
            if (i % 6 == 0) {
                canvas.drawLine(mScaleRadius - 5, 0, mScaleRadius, 0, mScalePaint);
            }
            canvas.rotate(1);
        }
        canvas.restore();
    }

    private void drawText(Canvas canvas) {

    }

    /**
     * 根据时间更新UI.
     *
     * @param hour   小时
     * @param minute 分钟
     * @param second 秒钟
     */
    public void updateTime(int hour, int minute, int second) {
        mSecondDegree = second * 6;
        invalidate();
    }

    private ScheduledExecutorService mExecutorService;
    private int hour, minute, second;

    /**
     * 使用View内部的时钟机制刷新UI.
     */
    public void start() {
        second = 0;
        if (mExecutorService == null || mExecutorService.isShutdown()) {
            mExecutorService = Executors.newScheduledThreadPool(1);
        }
        mExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                second++;
                updateTime(0, 0, second);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
