package com.wujia.support.log;

public interface HiLogFormatter<T> {

    String format(T data);
}