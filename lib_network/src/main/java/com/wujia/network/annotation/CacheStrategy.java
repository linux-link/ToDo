package com.wujia.network.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@IntDef({Strategy.CACHE_FIRST, Strategy.NET_FIRST, Strategy.NET_ONLY, Strategy.CACHE_ONLY})
public @interface CacheStrategy {
    int value() default Strategy.NET_ONLY;
}

class Strategy {
    public static final int CACHE_FIRST = 1;
    public static final int NET_FIRST = 2;
    public static final int NET_ONLY = 3;
    public static final int CACHE_ONLY = 4;
}
