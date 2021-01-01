package com.wujia.ui.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.appcompat.widget.AppCompatButton;

import com.wujia.ui.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author wujia0916.
 * @date 20-3-31
 * @desc Button, supporting multiple states
 */
public class StateButton extends AppCompatButton {

    @IntDef({ACTION_NORMAL, ACTION_PRESSED, ACTION_DISABLE, ACTION_OTHER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Action {
    }

    public static final int DEFAULT_VALUE = -1;

    public static final int ACTION_DISABLE = 0;
    public static final int ACTION_NORMAL = 1;
    public static final int ACTION_PRESSED = 2;
    public static final int ACTION_OTHER = 3;

    private int mAction;

    @DrawableRes
    private int mNormalResId;
    @DrawableRes
    private int mPressedResId;
    @DrawableRes
    private int mDisableResId;
    @DrawableRes
    private int mOtherResId;

    public StateButton(Context context) {
        this(context, null);
    }

    public StateButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Create StateButton.
     * @param context context
     * @param attrs attrs
     * @param defStyleAttr defStyleAttr
     */
    public StateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StateButton);
        mNormalResId = typedArray.getResourceId(
                R.styleable.StateButton_state_normal, DEFAULT_VALUE);
        mPressedResId = typedArray.getResourceId(
                R.styleable.StateButton_state_pressed, DEFAULT_VALUE);
        mDisableResId = typedArray.getResourceId(
                R.styleable.StateButton_state_disable, DEFAULT_VALUE);
        mOtherResId = typedArray.getResourceId(
                R.styleable.StateButton_state_other, DEFAULT_VALUE);
        mAction = typedArray.getResourceId(
                R.styleable.StateButton_default_state, ACTION_NORMAL);
        typedArray.recycle();
        setAction(mAction);
        setClickable(true);
    }


    /**
     * Sets the action to display on this view.
     *
     * @param action one of {@link Action}
     */
    public void setAction(@Action int action) {
        mAction = action;
        setEnabled(true);
        setClickable(true);
        switch (mAction) {
            case ACTION_PRESSED:
                if (DEFAULT_VALUE != mPressedResId) {
                    setBackgroundResource(mPressedResId);
                }
                break;
            case ACTION_DISABLE:
                if (DEFAULT_VALUE != mDisableResId) {
                    setBackgroundResource(mDisableResId);
                }
                setEnabled(false);
                setClickable(false);
                break;
            case ACTION_OTHER:
                if (DEFAULT_VALUE != mOtherResId) {
                    setBackgroundResource(mOtherResId);
                }
                break;
            case ACTION_NORMAL:
            default:
                if (DEFAULT_VALUE != mNormalResId) {
                    setBackgroundResource(mNormalResId);
                } else {
                    setBackground(null);
                }
                break;
        }
    }

    public int getAction() {
        return mAction;
    }

    @Override
    public boolean performClick() {
        if (mAction == ACTION_NORMAL) {
            setAction(ACTION_PRESSED);
        } else {
            setAction(ACTION_NORMAL);
        }
        return super.performClick();
    }
}
