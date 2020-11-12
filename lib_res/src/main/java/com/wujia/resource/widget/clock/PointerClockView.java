package com.wujia.resource.widget.clock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wujia.resource.R;

public class PointerClockView extends View {

    private int mViewWidth, mViewHeight;

    private Paint mHourHandPaint;
    private Paint mMinuteHandPaint;
    private Paint mSecondHandPaint;
    private Paint mScalePaint;

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
        int hourColor = typedArray.getColor(R.styleable.PointerClockView_hourHandColor, Color.BLACK);
        mHourHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHourHandPaint.setColor(hourColor);

        int minuteColor = typedArray.getColor(R.styleable.PointerClockView_minuteHandColor, Color.BLACK);
        mMinuteHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMinuteHandPaint.setColor(minuteColor);

        int secondColor = typedArray.getColor(R.styleable.PointerClockView_secondHandColor, Color.RED);
        mSecondHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondHandPaint.setColor(secondColor);

        int scaleColor = typedArray.getColor(R.styleable.PointerClockView_scaleColor, Color.GRAY);
        mScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScalePaint.setColor(scaleColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCompass(canvas);
        drawPointer(canvas);
    }

    private void drawPointer(Canvas canvas) {
    }

    private void drawCompass(Canvas canvas) {
        mScalePaint.setStrokeWidth(10);
        canvas.drawPoint(mViewWidth / 2f, mViewHeight / 2f, mScalePaint);

        mScalePaint.setStrokeWidth(5);
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        for (int i = 0; i < 360; i++) {
            if (i % 30 == 0) {
                canvas.drawLine(mViewWidth / 3 - 40, 0, mViewWidth / 3, 0, mScalePaint);
            }else if (i%6==0){
                canvas.drawLine(mViewWidth / 3 - 5, 0, mViewWidth / 3, 0, mScalePaint);
            }
            canvas.rotate(1);
        }

        mScalePaint.setTextSize(35);
        mScalePaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 12; i++) {
            if (i == 0) {
                drawNum(canvas, i * 30, 12 + "", mScalePaint);
            } else {
                drawNum(canvas, i * 30, i + "", mScalePaint);
            }
        }
    }

    private void drawNum(Canvas canvas,int degree, String text, Paint paint) {
        Rect textBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBound);
        canvas.rotate(degree);
        canvas.translate(0, 70 - getWidth() / 3);
        canvas.rotate(-degree);
        canvas.drawText(text, -textBound.width() / 2,
                textBound.height() / 2, paint);
        canvas.rotate(degree);
        canvas.translate(0, getWidth() / 3 - 70);
        canvas.rotate(-degree);
    }
}
