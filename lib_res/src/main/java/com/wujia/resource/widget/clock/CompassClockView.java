package com.wujia.resource.widget.clock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.wujia.resource.R;
import com.wujia.resource.utils.DensityUtils;

public class CompassClockView extends View {

    private int mViewWidth, mViewHeight;
    private int mCentreX, mCentreY;

    private int mTimeFormatCompassColor;
    private int mTimeFormatCompassRadius;
    private int mMinuteCompassColor;
    private int mMinuteCompassRadius;
    private int mSecondCompassColor;
    private int mSecondCompassRadius;

    private Paint mSecPaint, mMinPaint, mFormatPaint;

    public CompassClockView(Context context) {
        super(context, null);
    }

    public CompassClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mSecPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMinPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFormatPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CompassClockView);
        mSecondCompassColor = array.getColor(R.styleable.CompassClockView_compass_second_color, Color.YELLOW);
        mMinuteCompassColor = array.getColor(R.styleable.CompassClockView_compass_minute_color, Color.argb(255, 255, 215, 50));
        mTimeFormatCompassColor = array.getColor(R.styleable.CompassClockView_compass_format_color, Color.BLACK);
        array.recycle();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight = h;
        mViewWidth = w;
        mCentreX = w / 2;
        mCentreY = h / 2;

        mTimeFormatCompassRadius = w / 4;
        mMinuteCompassRadius = mTimeFormatCompassRadius * 2;
        mSecondCompassRadius = mTimeFormatCompassRadius * 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSecond(canvas);
        drawMinute(canvas);
        drawTimeFormat(canvas);
    }

    private void drawSecond(Canvas canvas) {
        canvas.save();
        canvas.translate(mCentreX, 0);
        mSecPaint.setColor(mSecondCompassColor);
        mSecPaint.setShadowLayer(10, 0, 0, mSecondCompassColor);
//        mSecPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.INNER));
        canvas.drawCircle(0, 0, mSecondCompassRadius, mSecPaint);

        mSecPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 12; i++) {
            if (i == 0) {
                drawSecNum(canvas, i * 30, String.valueOf(60), mSecPaint);
            } else {
                drawSecNum(canvas, i * 30, String.valueOf(i * 5), mSecPaint);
            }
        }

        canvas.restore();
    }

    private void drawSecNum(Canvas canvas, int degree, String text, Paint paint) {
        if (degree == 180) {
            paint.setTextSize(DensityUtils.sp2px(50));
            paint.setColor(Color.BLACK);
        } else {
            paint.setTextSize(DensityUtils.sp2px(30));
            paint.setColor(getContext().getResources().getColor(R.color.grey_alpha));
        }
        canvas.save();
        canvas.rotate(degree);
        Rect textBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBound);

        canvas.translate(0, -((mSecondCompassRadius - mMinuteCompassRadius) / 2f + mMinuteCompassRadius));
        canvas.rotate(180);
        canvas.drawText(text, -textBound.width() / 2f, textBound.height() / 2f, paint);
        canvas.restore();
    }

    private void drawMinute(Canvas canvas) {
        canvas.save();
        canvas.translate(mCentreX, 0);
        mMinPaint.setShadowLayer(10, 0, 0, mMinuteCompassColor);
        mMinPaint.setColor(mMinuteCompassColor);
        canvas.drawCircle(0, 0, mMinuteCompassRadius, mMinPaint);


        mMinPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 12; i++) {
            if (i == 0) {
                drawNum(canvas, i * 30, String.valueOf(12), mMinPaint);
            } else {
                drawNum(canvas, i * 30, String.valueOf(i), mMinPaint);
            }
        }
        canvas.restore();
    }

    private void drawNum(Canvas canvas, int degree, String text, Paint paint) {
        if (degree == 180) {
            paint.setTextSize(DensityUtils.sp2px(50));
            paint.setColor(Color.BLACK);
        } else {
            paint.setTextSize(DensityUtils.sp2px(30));
            paint.setColor(getContext().getResources().getColor(R.color.grey_alpha));
        }
        canvas.save();
        canvas.rotate(degree);
        Rect textBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBound);

        canvas.translate(0, -((mMinuteCompassRadius - mTimeFormatCompassRadius) / 2f + mTimeFormatCompassRadius));
        canvas.rotate(180);
        canvas.drawText(text, -textBound.width() / 2f, textBound.height() / 2f, paint);
        canvas.restore();
    }

    private void drawTimeFormat(Canvas canvas) {
        canvas.save();
        canvas.translate(mCentreX, 0);
        mFormatPaint.setShadowLayer(10, 0, 0, mTimeFormatCompassColor);
        mFormatPaint.setColor(mTimeFormatCompassColor);
        canvas.drawCircle(0, 0, mTimeFormatCompassRadius, mFormatPaint);

        mFormatPaint.setColor(Color.WHITE);
        mFormatPaint.setTextSize(DensityUtils.sp2px(30));
        Rect textBound = new Rect();
        mFormatPaint.getTextBounds("AM", 0, "AM".length(), textBound);
        canvas.drawText("AM", -textBound.width() / 2f, mTimeFormatCompassRadius / 2f, mFormatPaint);

        canvas.restore();
    }

    public void updateTime(int hour, int minute, int second) {

    }
}
