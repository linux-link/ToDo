package com.wujia.todo.clock.ui.home;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wujia.resource.utils.DensityUtils;
import com.wujia.todo.clock.R;


public class BookPageView2 extends View {

    public static final String TAG = BookPageView2.class.getSimpleName();

    public static final int IMAGE_WIDTH = DensityUtils.dp2px(400);
    public static final int SLIDE_DISTANCE = DensityUtils.dp2px(200);
    public static final int MIN_SLIDE_DISTANCE = DensityUtils.dp2px(10);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Camera mCamera = new Camera();

    private Bitmap mBitmap;
    private int offsetX, offsetY;
    private float rotateX;

    private float x1, y1, x2, y2;


    public BookPageView2(Context context) {
        super(context, null);
    }

    public BookPageView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBitmap = getAvatar(getResources(), IMAGE_WIDTH);
        mCamera.setLocation(0, 0, -6 * getResources().getDisplayMetrics().density);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                y2 = event.getY();
                if (y1 - y2 > 50) {
                    double distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                    Log.e(TAG, "onTouchEvent: " + distance / SLIDE_DISTANCE * 180);
                    int rotate = (int) (distance / SLIDE_DISTANCE * 180);
                    if (rotate > 180) {
                        rotateX = 180f;
                    } else {
                        rotateX = rotate;
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (y1 - y2 > 50) {
                    double distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                    Log.e(TAG, "onTouchEvent: " + distance / SLIDE_DISTANCE * 180);
                    int rotate = (int) (distance / SLIDE_DISTANCE * 180);
                    if (rotate > 180) {
                        rotateX = 180f;
                    } else {
                        rotateX = rotate;
                    }
                    invalidate();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("onSizeChanged: ", w + "," + h);
        offsetX = (int) ((getWidth() - mBitmap.getWidth()) / 2f);
        offsetY = (int) ((getHeight() - mBitmap.getHeight()) / 2f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(offsetX + IMAGE_WIDTH / 2f, offsetY + IMAGE_WIDTH / 2f);
        canvas.clipRect(-mBitmap.getWidth() / 2, -mBitmap.getWidth() / 2, mBitmap.getWidth(), 0);
        canvas.translate(-(offsetX + IMAGE_WIDTH / 2f), -(offsetY + IMAGE_WIDTH / 2f));
        canvas.drawBitmap(getAvatar(getResources(), IMAGE_WIDTH), offsetX, offsetY, mPaint);
        canvas.restore();

        canvas.save();
        mCamera.save();
        mCamera.rotateX(rotateX);
        canvas.translate(offsetX + IMAGE_WIDTH / 2f, offsetY + IMAGE_WIDTH / 2f);
        mCamera.applyToCanvas(canvas);
        canvas.clipRect(-mBitmap.getWidth() / 2, 0, mBitmap.getWidth() / 2, mBitmap.getWidth() / 2);
        canvas.translate(-(offsetX + IMAGE_WIDTH / 2f), -(offsetY + IMAGE_WIDTH / 2f));
        mCamera.restore();
        canvas.drawBitmap(getAvatar(getResources(), IMAGE_WIDTH), offsetX, offsetY, mPaint);
        canvas.restore();

    }

    public static Bitmap getAvatar(Resources res, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, R.drawable.background, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(res, R.drawable.background, options);
    }

    public float getRotateX() {
        return rotateX;
    }

    public void setRotateX(float rotateX) {
        this.rotateX = rotateX;
        invalidate();
    }

    private ObjectAnimator mRotateAnimator;

    private ObjectAnimator getRotateAnimator() {
        if (mRotateAnimator == null) {
            mRotateAnimator = ObjectAnimator.ofFloat(this, "rotateX", rotateX, 180f);
        }
        return mRotateAnimator;
    }
}
