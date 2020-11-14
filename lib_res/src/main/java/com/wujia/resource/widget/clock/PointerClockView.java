package com.wujia.resource.widget.clock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.wujia.resource.R;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 指针时钟View.
 *
 * @author WuJia.
 * @version 1.0
 * @date 11/15/20
 */
public class PointerClockView extends View {

    private int mViewWidth, mViewHeight;

    private int mHourHandColor;
    private int mMinuteHandColor;
    private int mSecondHandColor;
    private int mScaleHandColor;
    private Paint mCompassPaint;
    private Paint mPointerPaint;

    private int mCompassRadius;
    private int mCenterX, mCenterY;

    private float mHourDegree, mMinuteDegree, mSecondDegree;

    public PointerClockView(Context context) {
        super(context, null);
    }

    public PointerClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PointerClockView);
        initPaint(typedArray);
        typedArray.recycle();
    }

    private void initPaint(TypedArray typedArray) {
        mHourHandColor = typedArray.getColor(R.styleable.PointerClockView_hourHandColor, Color.BLACK);
        mMinuteHandColor = typedArray.getColor(R.styleable.PointerClockView_minuteHandColor, Color.BLACK);
        mSecondHandColor = typedArray.getColor(R.styleable.PointerClockView_secondHandColor, Color.RED);

        mScaleHandColor = typedArray.getColor(R.styleable.PointerClockView_scaleColor, Color.GRAY);
        mPointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointerPaint.setStrokeCap(Paint.Cap.ROUND);
        mPointerPaint.setShadowLayer(3,0,0,Color.BLACK);

        mCompassPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCompassPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mCompassRadius = mViewWidth / 3;
        mCenterX = mViewWidth / 2;
        mCenterY = mViewHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPointer(canvas);
        drawCompass(canvas);
    }

    /******************************* inner function.***********************************************/

    private void drawPointer(Canvas canvas) {
        //秒针
        canvas.save();
        mPointerPaint.setColor(mSecondHandColor);
        mPointerPaint.setStyle(Paint.Style.STROKE);
        mPointerPaint.setStrokeWidth(6);
        // 旋转坐标系
        canvas.rotate(mSecondDegree, mCenterX, mCenterY);
        int secondY = mViewHeight / 2 - mCompassRadius + 100;
        canvas.drawLine(mCenterX, mCenterY, mCenterX, secondY, mPointerPaint);
        canvas.restore();

        //分针
        canvas.save();
        mPointerPaint.setColor(mMinuteHandColor);
        mPointerPaint.setStrokeWidth(10);
        canvas.rotate(mMinuteDegree, mCenterX, mCenterY);
        int minuteY = mViewHeight / 2 - mCompassRadius + 150;
        canvas.drawLine(mCenterX, mCenterY, mCenterX, minuteY, mPointerPaint);
        canvas.restore();

        //时针
        canvas.save();
        mPointerPaint.setColor(mHourHandColor);
        mPointerPaint.setStrokeWidth(14);
        canvas.rotate(mHourDegree, mCenterX, mCenterY);
        int hourY = mViewHeight / 2 - mCompassRadius + 220;
        canvas.drawLine(mCenterX, mCenterY, mCenterX, hourY, mPointerPaint);
        canvas.restore();
    }

    private void drawCompass(Canvas canvas) {
        canvas.save();
        mCompassPaint.setColor(mHourHandColor);
        mCompassPaint.setStrokeWidth(30);
        canvas.drawPoint(mViewWidth / 2f, mViewHeight / 2f, mCompassPaint);

        mCompassPaint.setColor(mScaleHandColor);
        mCompassPaint.setStrokeWidth(5);
        canvas.translate(mViewWidth / 2f, mViewHeight / 2f);
        for (int i = 0; i < 360; i++) {
            if (i % 30 == 0) {
                canvas.drawLine(mViewWidth / 3f - 40, 0, mCompassRadius, 0, mCompassPaint);
            } else if (i % 6 == 0) {
                canvas.drawLine(mViewWidth / 3f - 5, 0, mCompassRadius, 0, mCompassPaint);
            }
            canvas.rotate(1);
        }

        mCompassPaint.setTextSize(35);
        mCompassPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 12; i++) {
            if (i == 0) {
                drawNum(canvas, i * 30, 12 + "", mCompassPaint);
            } else {
                drawNum(canvas, i * 30, i + "", mCompassPaint);
            }
        }
        canvas.restore();
    }

    private void drawNum(Canvas canvas, int degree, String text, Paint paint) {
        Rect textBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBound);
        canvas.rotate(degree);
        canvas.translate(0, 70 - getWidth() / 3f);
        canvas.rotate(-degree);
        canvas.drawText(text, -textBound.width() / 2f,
                textBound.height() / 2f, paint);
        canvas.rotate(degree);
        canvas.translate(0, getWidth() / 3f - 70);
        canvas.rotate(-degree);
    }

    /******************************* inner function end.*******************************************/

    /******************************* expose function.**********************************************/

    /**
     * 根据时间更新UI.
     *
     * @param hour   小时
     * @param minute 分钟
     * @param second 秒钟
     */
    public void updateTime(int hour, int minute, int second) {
        mSecondDegree = second * 6f;
        mMinuteDegree = (minute + second / 60f) * 6f;
        mHourDegree = (hour + minute / 60f + second / 360f) * 30f;
        invalidate();
    }

    private ScheduledExecutorService mExecutorService;

    /**
     * 使用View内部的时钟机制刷新UI.
     *
     * @param auto 是否自动刷新
     */
    public void autoUpdate(boolean auto) {
        if (auto) {
            if (mExecutorService == null || mExecutorService.isShutdown()) {
                mExecutorService = Executors.newScheduledThreadPool(1);
            }
            mExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    Calendar calendar = Calendar.getInstance();
                    // 加一秒，解决显示延迟的问题
                    int second = calendar.get(Calendar.SECOND) + 1;
                    int minute = calendar.get(Calendar.MINUTE);
                    int hour = calendar.get(Calendar.HOUR);
                    updateTime(hour, minute, second);
                }
            }, 0, 1, TimeUnit.SECONDS);
        } else {
            if (mExecutorService != null || !mExecutorService.isShutdown()) {
                mExecutorService.shutdown();
            }
            mExecutorService = null;
        }
    }

    /******************************* expose function end.******************************************/
}
