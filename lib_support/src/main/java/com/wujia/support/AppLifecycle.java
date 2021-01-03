package com.wujia.support;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 监听当前应用的生命周期
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/11/5
 */
public class AppLifecycle implements LifecycleObserver {

    private final static AppLifecycle APP_LIFECYCLE = new AppLifecycle();

    private final CopyOnWriteArrayList<IAppLifecycleListener> mListeners =
            new CopyOnWriteArrayList<>();

    private AppLifecycle() {
    }

    public static AppLifecycle getInstance() {
        return APP_LIFECYCLE;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onAppCreate() {
        for (IAppLifecycleListener listener : mListeners) {
            listener.onAppCreate();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppStart() {
        for (IAppLifecycleListener listener : mListeners) {
            listener.onAppStart();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAppResume() {
        for (IAppLifecycleListener listener : mListeners) {
            listener.onAppResume();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onAppPause() {
        for (IAppLifecycleListener listener : mListeners) {
            listener.onAppPause();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppStop() {
        for (IAppLifecycleListener listener : mListeners) {
            listener.onAppStop();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onAppDestroy() {
        // 永远不会被回调
    }

    public void addAppLifecycleListener(IAppLifecycleListener listener) {
        mListeners.add(listener);
    }

    public static class IAppLifecycleListener {

        public void onAppCreate() {
        }

        public void onAppStart() {
        }

        public void onAppResume() {
        }

        public void onAppPause() {
        }

        public void onAppStop() {
        }
    }

}
