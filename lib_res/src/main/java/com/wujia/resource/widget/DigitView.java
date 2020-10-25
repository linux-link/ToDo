package com.wujia.resource.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.wujia.resource.R;
import com.wujia.resource.utils.DensityUtils;

/**
 * 翻页显示组件
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/10/18
 */
public class DigitView extends View {

    private final char[] mChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    // attributeSet
    private int mTextSize;
    private int mCornerSize;
    private int mTextColor;
    private int mBoardColor;
    private int mDividerSize;

    // inner attributeSet
    private int mNextIndex = 1;
    private int mPreviousIndex = 0;
    private final int mDistance = 10;
    private float mRotateX = 0;
    private final Rect mTextRect = new Rect();

    // paint
    private Paint mNumberPaint, mBackgroundPaint, mDividerPaint;
    private final RectF mHeaderBackgroundRect = new RectF();
    private final RectF mFooterBackgroundRect = new RectF();
    private int mViewWidth, mViewHeight;
    private Camera mCamera;
    private ObjectAnimator mRotateAnimator;

    /********************************* init function. *********************************************/

    public DigitView(Context context) {
        super(context, null);
    }

    public DigitView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
        initPaints();
        initCamera();
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DigitView, 0, 0);
        mTextSize = array.getDimensionPixelSize(R.styleable.DigitView_textSize, DensityUtils.sp2px(20));
        mTextColor = array.getColor(R.styleable.DigitView_textColor, Color.BLACK);
        mCornerSize = array.getDimensionPixelSize(R.styleable.DigitView_cornersSize, DensityUtils.dp2px(5));
        mDividerSize = array.getDimensionPixelSize(R.styleable.DigitView_dividerSize, DensityUtils.dp2px(1));
        mBoardColor = array.getColor(R.styleable.DigitView_boardColor, Color.WHITE);
        array.recycle();
    }

    private void initPaints() {
        mNumberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumberPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mNumberPaint.setTextSize(DensityUtils.sp2px(mTextSize));
        mNumberPaint.setColor(mTextColor);

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(mBoardColor);

        mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ColorDrawable drawable = (ColorDrawable) getBackground();
        if (drawable != null) {
            mDividerPaint.setColor(drawable.getColor());
        }
        mDividerPaint.setStrokeWidth(mDividerSize);
    }

    private void initCamera() {
        mCamera = new Camera();
        mCamera.setLocation(0, 0, -10 * getResources().getDisplayMetrics().density);
    }

    /********************************* init function end. *****************************************/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawNumber(canvas);
        drawDividerLine(canvas);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        Log.e("TAG", "onSizeChanged: " + width + ";" + height);
        mViewWidth = width;
        mViewHeight = height;
    }

    /********************************* inner function. ********************************************/

    private void drawBackground(Canvas canvas) {
        int width = mViewWidth - mDistance * 2;
        int halfHeight = mViewHeight / 2 - mDividerSize / 2 - mDistance;
        //绘制圆角矩形的上半部分
        canvas.save();
        mHeaderBackgroundRect.set(mDistance, mDistance, width + mDistance, halfHeight + mDistance);
        canvas.drawRoundRect(mHeaderBackgroundRect, mCornerSize, mCornerSize, mBackgroundPaint);
        canvas.restore();

        //绘制圆角矩形的下半部分(底板)
        canvas.save();
        mFooterBackgroundRect.set(mDistance, halfHeight + mDistance + mDividerSize,
                width + mDistance, halfHeight * 2 + mDividerSize + mDistance);
        canvas.drawRoundRect(mFooterBackgroundRect, mCornerSize, mCornerSize, mBackgroundPaint);
        canvas.restore();

        //绘制圆角矩形的下半部分
        canvas.save();
        mCamera.save();
        mCamera.rotateX(mRotateX);
        canvas.translate(mViewWidth / 2f, mViewHeight / 2f);
        mCamera.applyToCanvas(canvas);
        canvas.translate(-mViewWidth / 2f, -mViewHeight / 2f);
        mCamera.restore();
        mFooterBackgroundRect.set(mDistance, halfHeight + mDistance + mDividerSize,
                width + mDistance, halfHeight * 2 + mDividerSize + mDistance);
        canvas.drawRoundRect(mFooterBackgroundRect, mCornerSize, mCornerSize, mBackgroundPaint);
        canvas.restore();
    }

    private void drawNumber(Canvas canvas) {
        mNumberPaint.getTextBounds("8", 0, 1, mTextRect);
        float textHeight = Math.abs(mTextRect.bottom + mTextRect.top);
        float textWidth = Math.abs(mTextRect.right + mTextRect.left);
        float offsetY = textHeight / 2f;
        float offsetX = textWidth / 2f;

        float baselineX = mViewWidth / 2f - offsetX;
        float baselineY = mViewHeight / 2f + offsetY;

        //绘制上半部分数字
        canvas.save();
        canvas.clipRect(mHeaderBackgroundRect);
        canvas.drawText(Character.toString(mChars[getPreviousIndex()]), 0, 1, baselineX, baselineY, mNumberPaint);
        canvas.restore();

        if (mRotateX >= 90) {
            //绘制上半部分数字（底板）
            canvas.save();
            canvas.clipRect(mHeaderBackgroundRect);
            mCamera.save();
            mCamera.rotateX(mRotateX - 180);
            canvas.translate(mViewWidth / 2f, mViewHeight / 2f);
            mCamera.applyToCanvas(canvas);
            canvas.translate(-mViewWidth / 2f, -mViewHeight / 2f);
            mCamera.restore();
            canvas.drawRoundRect(mHeaderBackgroundRect, mCornerSize, mCornerSize, mBackgroundPaint);
            canvas.drawText(Character.toString(mChars[mNextIndex]), 0, 1, baselineX, baselineY, mNumberPaint);
            canvas.restore();
        }

        //绘制下半部分数字（底板）
        canvas.save();
        canvas.clipRect(mFooterBackgroundRect);
        canvas.drawText(Character.toString(mChars[mNextIndex]), 0, 1, baselineX, baselineY, mNumberPaint);
        canvas.restore();

        if (mRotateX <= 90) {
            //绘制下半部分数字
            canvas.save();
            canvas.clipRect(mFooterBackgroundRect);
            mCamera.save();
            mCamera.rotateX(mRotateX);
            canvas.translate(mViewWidth / 2f, mViewHeight / 2f);
            mCamera.applyToCanvas(canvas);
            canvas.translate(-mViewWidth / 2f, -mViewHeight / 2f);
            mCamera.restore();
            canvas.drawRoundRect(mFooterBackgroundRect, mCornerSize, mCornerSize, mBackgroundPaint);
            canvas.drawText(Character.toString(mChars[getPreviousIndex()]), 0, 1, baselineX, baselineY, mNumberPaint);
            canvas.restore();
        }
    }

    private void drawDividerLine(Canvas canvas) {
        if (mRotateX >= 70 && mRotateX <= 130) {
            return;
        }
        int width = mViewWidth - mDistance * 2;
        canvas.save();
        canvas.drawLine(mDistance, mViewHeight / 2f, mDistance + width, mViewHeight / 2f, mDividerPaint);
        canvas.restore();
    }

    private int getPreviousIndex() {

        int index = mNextIndex - 1;
        if (index == -1) {
            index = mPreviousIndex + 1;
        } else {
            mPreviousIndex = index;
        }
        return index;
    }

    private ObjectAnimator getRotateAnimator() {
        if (mRotateAnimator == null) {
            mRotateAnimator = ObjectAnimator.ofFloat(this, "rotateX", mRotateX, 180f);
        }
        mRotateAnimator.setInterpolator(new LinearInterpolator());
        mRotateAnimator.cancel();
        mRotateAnimator.setDuration(900L);
        return mRotateAnimator;
    }

    /********************************* inner function end. ****************************************/

    /********************************* expose function. *******************************************/

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
        if (background instanceof ColorDrawable) {
            mDividerPaint.setColor(((ColorDrawable) background).getColor());
        }
    }

    public float getRotateX() {
        return mRotateX;
    }

    public void setRotateX(float rotateX) {
        this.mRotateX = rotateX;
        postInvalidate();
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
    }

    public void setCornerSize(int cornerSize) {
        mCornerSize = cornerSize;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        mNumberPaint.setColor(mTextColor);
    }

    public void setBoardColor(int boardColor) {
        mBoardColor = boardColor;
        mBackgroundPaint.setColor(mBoardColor);
    }

    public void setDividerSize(int dividerSize) {
        mDividerSize = dividerSize;
    }

    public void start(int index) {
        mNextIndex = index;
        mRotateX = 0;
        getRotateAnimator().start();
    }

}
