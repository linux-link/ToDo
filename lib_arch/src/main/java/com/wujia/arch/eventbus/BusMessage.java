package com.wujia.arch.eventbus;

/**
 * @author wujia0916.
 * @date 20-3-18
 * @desc Event bus message.
 */
public class BusMessage<T> {

    private int mTarget;
    private T mData;

    public int getTarget() {
        return mTarget;
    }

    public void setTarget(int target) {
        mTarget = target;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }
}
