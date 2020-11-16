package com.wujia.resource.button;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.wujia.resource.R;


public class MaterialSwitch extends View {

    private static final String TAG = MaterialSwitch.class.getSimpleName();

    private Paint mTriggerPaint;
    private Paint mBackgroundAreaPaint;
    private Paint mFlagPaint;
    @ColorInt
    private int mSmartSwitchColor;

    int indicatorR;
    int mViewWidth = 420, mViewHeight = 263;
    /**
     * 背景圆角矩形的内阴影大小
     */
    int backgroundAreaShadowSize = 24;
    /**
     * 背景圆角矩形的宽高
     */
    int backgroundAreaW, backgroundAreaH;
    int shadowOffset;
    int indicatorX;

    int indicatorXOffset;

    public MaterialSwitch(Context context) {
        super(context, null);
    }

    public MaterialSwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.MaterialSwitch);
        mSmartSwitchColor = typedArray.getColor(R.styleable.MaterialSwitch_material_color,
                context.getResources().getColor(android.R.color.white));
        typedArray.recycle();

        mTriggerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTriggerPaint.setColor(mSmartSwitchColor);

        mBackgroundAreaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundAreaPaint.setColor(mSmartSwitchColor);

        mFlagPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int viewWidth = mViewWidth;
        int viewHeight = mViewHeight;
        if (widthMode != MeasureSpec.AT_MOST) {
            viewWidth = width;
        }
        if (heightMode != MeasureSpec.AT_MOST) {
            viewHeight = height;
        }
        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        this.backgroundAreaW = this.mViewWidth * 3 / 4;
        this.backgroundAreaH = this.mViewHeight / 2;
        Log.e(TAG, "onMeasure: " + backgroundAreaH + ";" + backgroundAreaW);
        this.indicatorR = backgroundAreaH / 2;
        indicatorX = ((mViewWidth - backgroundAreaW) / 2) + indicatorR;
        setMeasuredDimension(mViewWidth, mViewHeight);
        Log.e(TAG, "onMeasure:indicatorR " + indicatorR);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFlagCircle(canvas);
        drawBackground(canvas);
        drawTrigger(canvas);
    }

    private void drawBackground(Canvas canvas) {
        canvas.save();

        mBackgroundAreaPaint.setStyle(Paint.Style.STROKE);
        int strokeW = 32;
        mBackgroundAreaPaint.setStrokeWidth(strokeW);
        mBackgroundAreaPaint.setColor(Color.parseColor("#ffbcbcbc"));
        int shadowRadius = 32;
        int shadowDy = 10;
        // 绘制大范围的阴影
        mBackgroundAreaPaint.setShadowLayer(shadowRadius + shadowOffset, 0, shadowDy, Color.GRAY);
        RectF strokeRectF = new RectF(-strokeW + (mViewWidth - backgroundAreaW) / 2, -strokeW + (mViewHeight - backgroundAreaH) / 2, strokeW + (mViewWidth - backgroundAreaW) / 2 + backgroundAreaW, strokeW + (mViewHeight - backgroundAreaH) / 2 + backgroundAreaH);
        Path strokePath = new Path();
        strokePath.addRoundRect(strokeRectF, (backgroundAreaH + strokeW) / 2, (backgroundAreaH + strokeW) / 2, Path.Direction.CW);
        // 裁出我们需要的阴影范围
        RectF rectF = new RectF((mViewWidth - backgroundAreaW) / 2, (mViewHeight - backgroundAreaH) / 2, (mViewWidth - backgroundAreaW) / 2 + backgroundAreaW, (mViewHeight - backgroundAreaH) / 2 + backgroundAreaH);
        Path path = new Path();
        path.addRoundRect(rectF, backgroundAreaH / 2, backgroundAreaH / 2, Path.Direction.CW);
        canvas.clipPath(path);

        canvas.drawPath(strokePath, mBackgroundAreaPaint);
        // 绘制出边界
        mBackgroundAreaPaint.setStrokeWidth(2);
        mBackgroundAreaPaint.clearShadowLayer();
        canvas.drawPath(path, mBackgroundAreaPaint);

        canvas.restore();
    }

    private void drawFlagCircle(Canvas canvas) {
        canvas.save();
        // 首先裁剪出背景圆角矩形画布
        RectF rectF = new RectF((mViewWidth - backgroundAreaW) / 2,
                (mViewHeight - backgroundAreaH) / 2,
                (mViewWidth - backgroundAreaW) / 2 + backgroundAreaW,
                (mViewHeight - backgroundAreaH) / 2 + backgroundAreaH);
        Path bgAreaPath = new Path();
        bgAreaPath.addRoundRect(rectF, backgroundAreaH / 2, backgroundAreaH / 2, Path.Direction.CW);
        canvas.clipPath(bgAreaPath);

        //绘制on flag
        mFlagPaint.setStyle(Paint.Style.FILL);
        mFlagPaint.setColor(Color.parseColor("#ff9ab9ff"));
        mFlagPaint.clearShadowLayer();
        canvas.drawCircle(indicatorX + indicatorXOffset - backgroundAreaW * 3 / 5, mViewHeight / 2, indicatorR / 4, mFlagPaint);

        //内阴影
        mFlagPaint.setStyle(Paint.Style.STROKE);
        int onStrokeW = 16;
        mFlagPaint.setStrokeWidth(onStrokeW);
        mFlagPaint.setShadowLayer(onStrokeW, -onStrokeW, onStrokeW, Color.parseColor("#ff9ab9ff"));
        Path onPath = new Path();
        onPath.addCircle(indicatorX + indicatorXOffset - backgroundAreaW * 3 / 5, mViewHeight / 2, indicatorR / 4 + onStrokeW / 2, Path.Direction.CW);

        canvas.save();
        canvas.clipPath(onPath);
        canvas.drawPath(onPath, mFlagPaint);
        mFlagPaint.clearShadowLayer();
        canvas.restore();

        //绘制off flag
        mFlagPaint.setStyle(Paint.Style.FILL);
        mFlagPaint.setColor(Color.parseColor("#fff5f5f5"));
        canvas.drawCircle(indicatorX + indicatorXOffset + backgroundAreaW * 3 / 5, mViewHeight / 2, indicatorR / 4, mFlagPaint);

        //内阴影
        mFlagPaint.setStyle(Paint.Style.STROKE);
        int offStrokeW = indicatorR / 4;
        mFlagPaint.setStrokeWidth(offStrokeW);
        mFlagPaint.setShadowLayer(offStrokeW, -offStrokeW, offStrokeW, Color.parseColor("#fff5f5f5"));

        Path offPath = new Path();
        offPath.addCircle(indicatorX + indicatorXOffset + backgroundAreaW * 3 / 5, mViewHeight / 2, indicatorR / 4 + offStrokeW / 2, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(offPath);
        canvas.drawPath(offPath, mFlagPaint);
        canvas.restore();
        canvas.restore();

    }

    private void drawTrigger(Canvas canvas) {
        //绘制外阴影
        mTriggerPaint.setColor(Color.WHITE);
        mTriggerPaint.setStyle(Paint.Style.FILL);
        int triggerShadowSize = indicatorR / 3;
        int triggerShadowDistance = triggerShadowSize / 2;
        mTriggerPaint.setShadowLayer(triggerShadowSize - shadowOffset,
                0, triggerShadowDistance, Color.parseColor("#ffc1c1c1"));
        canvas.drawCircle(indicatorX + indicatorXOffset, (mViewHeight - backgroundAreaH) / 2 + indicatorR, indicatorR, mTriggerPaint);

        //绘制内阴影
        canvas.save();
        mTriggerPaint.setColor(Color.parseColor("#ffbcbcbc"));
        int strokeW = indicatorR / 2;
        mTriggerPaint.setStrokeWidth(strokeW);
        mTriggerPaint.setStyle(Paint.Style.STROKE);
        mTriggerPaint.setShadowLayer(indicatorR / 3,
                -indicatorR / 6,
                -indicatorR / 6,
                Color.parseColor("#fff1f1f1"));

        Path strokePath = new Path();
        strokePath.addCircle(indicatorX + indicatorXOffset,
                (mViewHeight - backgroundAreaH) / 2 + indicatorR,
                indicatorR + strokeW / 2,
                Path.Direction.CW);

        Path path = new Path();
        path.addCircle(indicatorX + indicatorXOffset,
                (mViewHeight - backgroundAreaH) / 2 + indicatorR,
                indicatorR,
                Path.Direction.CW);
        canvas.clipPath(path);

        canvas.drawPath(strokePath, mTriggerPaint);

        mTriggerPaint.setStrokeWidth(2);
        mTriggerPaint.clearShadowLayer();
        canvas.drawPath(path, mTriggerPaint);

        canvas.restore();
    }

    private ValueAnimator mShadowAnimator, mTriggerAnimator;
    private boolean mIsChecked;

    private void startTriggerAnimator(boolean isChecked) {
        if (mTriggerAnimator != null) {
            mTriggerAnimator.cancel();
        }
        if (isChecked) {
            mTriggerAnimator = ValueAnimator.ofInt(indicatorX + indicatorXOffset, mViewWidth - (mViewWidth - backgroundAreaW) / 2 - indicatorR);
        } else {
            mTriggerAnimator = ValueAnimator.ofInt(indicatorX + indicatorXOffset, (mViewWidth - backgroundAreaW) / 2 + indicatorR);
        }
        mTriggerAnimator.setDuration(300L);
        mTriggerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                indicatorX = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mTriggerAnimator.start();
    }

    private void onShadowAnimator() {
        if (mShadowAnimator != null) {
            mShadowAnimator.cancel();
        }
        mShadowAnimator = ValueAnimator.ofInt(0, indicatorR / 4);
        mShadowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                shadowOffset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mShadowAnimator.setDuration(200L);
        mShadowAnimator.start();
    }

    private void offShadowAnimator() {
        if (mShadowAnimator != null) {
            mShadowAnimator.cancel();
        }
        mShadowAnimator = ValueAnimator.ofInt(indicatorR / 4, 0);
        mShadowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                shadowOffset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mShadowAnimator.setDuration(200L);
        mShadowAnimator.start();
    }

    private int mPressX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPressX = (int) event.getX();
                onShadowAnimator();
                break;
            case MotionEvent.ACTION_UP:
                mPressX = 0;
                indicatorX = indicatorX + indicatorXOffset;
                offShadowAnimator();
                //移动动画,切换动画
                if (Math.abs(indicatorXOffset) <= 20) {
                    //视作点击
                    mIsChecked = !mIsChecked;
                    startTriggerAnimator(mIsChecked);
                } else if ((indicatorXOffset > 0 && indicatorXOffset >= (backgroundAreaW - 2 * indicatorR) / 2) || (indicatorXOffset < 0 && indicatorXOffset > -(backgroundAreaW - 2 * indicatorR) / 2)) {
                    indicatorXOffset = 0;
                    //切换状态:ON
                    mIsChecked = true;
                    startTriggerAnimator(true);
                } else if ((indicatorXOffset > 0 && indicatorXOffset < (backgroundAreaW - 2 * indicatorR) / 2) || (indicatorXOffset < 0 && indicatorXOffset <= -(backgroundAreaW - 2 * indicatorR) / 2)) {
                    indicatorXOffset = 0;
                    //切换状态:OFF
                    mIsChecked = false;
                    startTriggerAnimator(false);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                indicatorXOffset = (int) (event.getX() - mPressX);
                //边界判断
                if (indicatorX + indicatorXOffset <= (mViewWidth - backgroundAreaW) / 2 + indicatorR) {
                    indicatorXOffset = (mViewWidth - backgroundAreaW) / 2 + indicatorR - indicatorX;
                } else if (indicatorX + indicatorXOffset >= mViewWidth - (mViewWidth - backgroundAreaW) / 2 - indicatorR) {
                    indicatorXOffset = mViewWidth - (mViewWidth - backgroundAreaW) / 2 - indicatorR - indicatorX;
                }
                break;
            default:
                return true;
        }
        postInvalidate();
        return true;
    }
}
