package com.wujia.support.fps;

public class FpsMonitor {

    private static volatile FpsMonitor sInstance;
    private final FpsView mFpsView = new FpsView();

    private FpsMonitor() {
    }

    public static FpsMonitor getInstance() {
        if (sInstance == null) {
            synchronized (FpsMonitor.class) {
                if (sInstance == null) {
                    sInstance = new FpsMonitor();
                }
            }
        }
        return sInstance;
    }

    public void addListener(FpsListener listener) {
        mFpsView.addListener(listener);
    }

    public interface FpsListener {
        void onFrame(Double fps);
    }

    public void toggle(){
        mFpsView.toggle();
    }

}
