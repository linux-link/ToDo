package com.wujia.arch.eventbus;

import com.wujia.arch.mvvm.livedata.StickyLiveData;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author wujia0916.
 * @date 20-3-18
 * @desc Event bus achieved by LiveData
 */
public class LiveDataBus {

    private static class Lazy {
        static LiveDataBus sLiveDataBus = new LiveDataBus();
    }

    public static LiveDataBus getInstance() {
        return Lazy.sLiveDataBus;
    }

    private final HashMap<String, StickyLiveData> mHashMap = new HashMap<>();

    // Read write separation lock
    private ReentrantReadWriteLock mReadWriteLock = new ReentrantReadWriteLock();

    /**
     * Input event name.
     *
     * @param eventName event name
     * @return see {@link StickyLiveData}
     */
    public StickyLiveData with(String eventName) {
        StickyLiveData liveData = mHashMap.get(eventName);
        if (liveData == null) {
            liveData = new StickyLiveData(eventName);
            mReadWriteLock.writeLock().lock();
            try {
                mHashMap.put(eventName, liveData);
            } finally {
                mReadWriteLock.writeLock().unlock();
            }
            liveData.setDestroyListener(new StickyLiveData.IDestroyListener() {
                @Override
                public void onLiveDataDestroy(String eventName) {
                    mReadWriteLock.writeLock().lock();
                    try {
                        mHashMap.remove(eventName);
                    } finally {
                        mReadWriteLock.writeLock().unlock();
                    }
                }
            });
        }
        return liveData;
    }
}
