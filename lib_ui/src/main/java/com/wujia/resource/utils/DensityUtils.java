package com.wujia.resource.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class DensityUtils {

    private DensityUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, Resources.getSystem().getDisplayMetrics());
    }

    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, Resources.getSystem().getDisplayMetrics());
    }

    public static float px2dp(float pxVal) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    public static float px2sp(float pxVal) {
        return (pxVal / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }
}
