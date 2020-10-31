package com.wujia.arch.eventbus;

import com.wujia.arch.mvvm.livedata.StickyLiveData;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Event bus achieved by LiveData.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/10/31
 */
public class LiveDataBus {

    private static class Lazy {
        static LiveDataBus sLiveDataBus = new LiveDataBus();
    }

    public static LiveDataBus getInstance() {
        return Lazy.sLiveDataBus;
    }

    private final ConcurrentHashMap<String, StickyLiveData> mHashMap = new ConcurrentHashMap<>();

    /**
     * Input event name.
     *
     * @param channelName event name
     * @return see {@link StickyLiveData}
     */
    public StickyLiveData getChannel(String channelName) {
        StickyLiveData liveData = mHashMap.get(channelName);
        if (liveData == null) {
            liveData = new StickyLiveData(channelName);
            mHashMap.put(channelName, liveData);
            liveData.setDestroyListener(new StickyLiveData.IDestroyListener() {
                @Override
                public void onLiveDataDestroy(String eventName) {
                    mHashMap.remove(eventName);
                }
            });
        }
        return liveData;
    }
}
