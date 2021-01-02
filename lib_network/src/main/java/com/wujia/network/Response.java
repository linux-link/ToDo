package com.wujia.network;

public class Response<Data> {

    private String mOriginData;
    private int mCode;
    private Data mData;
    private String mErrorMsg;

    public String getOriginData() {
        return mOriginData;
    }

    public int getCode() {
        return mCode;
    }

    public Data getData() {
        return mData;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }
}
