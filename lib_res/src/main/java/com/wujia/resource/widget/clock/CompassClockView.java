package com.wujia.resource.widget.clock;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CompassClockView extends View {

    private int mTimeFormatColor;
    private int mMinuteCompassColor;
    private int mSecondCompassColor;

    public CompassClockView(Context context) {
        super(context, null);
    }

    public CompassClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    public void updateTime(int hour, int minute, int second) {

    }
}
