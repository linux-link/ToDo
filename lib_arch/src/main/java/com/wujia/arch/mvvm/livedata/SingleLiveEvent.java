package com.wujia.arch.mvvm.livedata;

import android.os.Looper;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Send event only once.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/9/1
 */
public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private final AtomicBoolean mPending = new AtomicBoolean(false);

    @MainThread
    @Override
    public void observe(LifecycleOwner owner, final Observer<? super T> observer) {
        // Observe the internal MutableLiveData
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T value) {
                if (mPending.compareAndSet(true, false)) {
                    observer.onChanged(value);
                }
            }
        });
    }

    @MainThread
    @Override
    public void setValue(@Nullable T value) {
        mPending.set(true);
        super.setValue(value);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    public void call() {
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            setValue(null);
        } else {
            postValue(null);
        }
    }
}
