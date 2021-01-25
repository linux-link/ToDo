package com.wujia.support.fps;

import android.util.Log;
import android.view.Choreographer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FrameMonitor {

    private final Choreographer choreographer = Choreographer.getInstance();
    private Long frameStartTime = 0L;//这个是记录 上一针到达的时间戳
    private int frameCount = 0;//1s 内确切绘制了多少帧
    private final List<FpsMonitor.FpsListener> listeners = new ArrayList<>();

    private final Choreographer.FrameCallback mFrameCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            long currentTimeMills = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos);
            if (frameStartTime > 0) {
                //计算两针之间的 时间差
                // 500ms  100ms
                long timeSpan = currentTimeMills - frameStartTime;
                //fps 每秒多少帧  frame per second
                frameCount++;
                if (timeSpan > 1000) {
                    double fps = frameCount * 1000.0 / timeSpan;
                    Log.d("FrameMonitor", fps+"");
                    for (FpsMonitor.FpsListener listener : listeners) {
                        listener.onFrame(fps);
                    }
                    frameCount = 0;
                    frameStartTime = currentTimeMills;
                }
            } else {
                frameStartTime = currentTimeMills;
            }
            start();
        }
    };

    public void start() {
        choreographer.postFrameCallback(mFrameCallback);
    }

    public void stop() {
        frameStartTime = 0L;
        choreographer.removeFrameCallback(mFrameCallback);
    }

    public void addListener(FpsMonitor.FpsListener listener) {
        listeners.add(listener);
    }
}
