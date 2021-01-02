package com.wujia.network;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HttpExcess {
    private List<NetInterceptor> mInterceptors;
    private ConcurrentHashMap<Method,MethodParser> mMethodService;

}
