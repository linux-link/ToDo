package com.wujia.network;

public interface Callback<Data> {

    void onSuccess(Response<Data> response);

    void onFailed(Throwable throwable);

}
