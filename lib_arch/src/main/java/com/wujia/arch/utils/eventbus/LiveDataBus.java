package com.wujia.arch.utils.eventbus;

import java.util.concurrent.ConcurrentHashMap;

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
            liveData.setDestroyListener(new StickyLiveData.IDestroyListener() {
                @Override
                public void onLiveDataDestroy(String channelName) {
                    mHashMap.remove(channelName);
                }
            });
            mHashMap.put(channelName, liveData);
        }
        return liveData;
    }
}
