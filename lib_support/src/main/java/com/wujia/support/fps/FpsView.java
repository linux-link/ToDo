package com.wujia.support.fps;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;

import com.wujia.support.AppGlobal;
import com.wujia.support.R;

import java.text.DecimalFormat;

public class FpsView {
    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private final DecimalFormat mDecimal = new DecimalFormat("#.0 fps");
    private final WindowManager mWindowManager;
    private boolean mIsPlaying;
    private final TextView mFpsView;

    private final FrameMonitor frameMonitor = new FrameMonitor();

    public FpsView() {
        mFpsView = (TextView) LayoutInflater.from(AppGlobal.getApplication()).inflate(R.layout.fps_view, null, false);
        mWindowManager = (WindowManager) AppGlobal.getApplication().getSystemService(Context.WINDOW_SERVICE);

        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.gravity = Gravity.END | Gravity.TOP;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }
        frameMonitor.addListener(fps -> mFpsView.setText(mDecimal.format(fps)));
    }

    private void stop() {
        frameMonitor.stop();
        if (mIsPlaying) {
            mIsPlaying = false;
            mWindowManager.removeView(mFpsView);
        }
    }

    private void play() {
        if (!hasOverlayPermission()) {
            startOverlaySettingActivity();
            Log.d("FpsView", "app has no overlay permission");
            return;
        }

        frameMonitor.start();
        if (!mIsPlaying) {
            mIsPlaying = true;
            mWindowManager.addView(mFpsView, mParams);
        }
    }

    private void startOverlaySettingActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + AppGlobal.getApplication().getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AppGlobal.getApplication().startActivity(intent);
        }
    }

    private boolean hasOverlayPermission() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(
                AppGlobal.getApplication()
        );
    }

    void toggle() {
        if (mIsPlaying) {
            stop();
        } else {
            play();
        }
    }

    void addListener(FpsMonitor.FpsListener listener) {
        frameMonitor.addListener(listener);
    }
}
