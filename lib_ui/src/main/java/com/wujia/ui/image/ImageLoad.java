package com.wujia.ui.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.wujia.ui.R;

/**
 * 图片载入框架.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/10/24
 */
public class ImageLoad {

    public static void load(Context context, String url, ImageView view) {
        Glide.with(context).load(url).apply(getOptions()).into(view);
    }

    public static void load(Context context, Drawable drawable, ImageView view) {
        Glide.with(context).load(drawable).apply(getOptions()).into(view);
    }

    public static void load(Context context, Bitmap bitmap, ImageView view) {
        Glide.with(context).load(bitmap).apply(getOptions()).into(view);
    }

    private static RequestOptions getOptions() {
        RequestOptions requestOptions = new RequestOptions().centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(new CenterCrop())
                .format(DecodeFormat.PREFER_RGB_565)
                .priority(Priority.LOW)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        return requestOptions;
    }
}
