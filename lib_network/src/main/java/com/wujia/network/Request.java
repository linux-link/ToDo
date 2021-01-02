package com.wujia.network;

import android.text.TextUtils;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private int mHttpMethod;
    private Map<String, String> mHeaders;
    private Map<String, String> mParameters;
    private String mDomainUrl;
    private String mRelativeUrl;
    private Type mReturnType;
    private boolean mFromPost;

    private String mCacheKey;
    private int mCacheStrategy;

    public String getEndPointUrl() {
        if (mRelativeUrl == null) {
            throw new IllegalStateException("relative url must bot be null ");
        }
        if (!mRelativeUrl.startsWith("/")) {
            return mDomainUrl + mRelativeUrl;
        }

        int indexOf = mDomainUrl.indexOf("/");
        return mDomainUrl.substring(0, indexOf) + mRelativeUrl;
    }

    public void addHeaders(String name,String value){
        if (mHeaders == null){
            mHeaders = new HashMap<>();
        }
        mHeaders.put(name,value);
    }

    public String getCacheKey() {
        if (!TextUtils.isEmpty(mCacheKey)) {
            return mCacheKey;
        }
        StringBuilder builder = new StringBuilder();
        String endUrl = getEndPointUrl();
        builder.append(endUrl);
        if (endUrl.indexOf("?") > 0 || endUrl.indexOf("&") > 0) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        if (mParameters != null) {
            for (Map.Entry<String,String> entity : mParameters.entrySet()){
                try {
                    String encodeValue = URLEncoder.encode(entity.getValue(), "UTF-8");
                    builder.append(entity.getKey()).append("=").append(encodeValue).append("&");
                } catch (Exception exception) {
                    //ignore
                }
            }
            builder.deleteCharAt(builder.length() - 1);
            mCacheKey = builder.toString();
        } else {
            mCacheKey = endUrl;
        }

        return mCacheKey;
    }

    public int getHttpMethod() {
        return mHttpMethod;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public Map<String, String> getParameters() {
        return mParameters;
    }

    public String getDomainUrl() {
        return mDomainUrl;
    }

    public String getRelativeUrl() {
        return mRelativeUrl;
    }

    public Type getReturnType() {
        return mReturnType;
    }

    public boolean isFromPost() {
        return mFromPost;
    }

    public int getCacheStrategy() {
        return mCacheStrategy;
    }
}
