package com.wujia.arch.utils.task;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread pool.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/10/13
 */
public final class TaskExecutors {

    public static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    private static final ThreadFactory CACHE_THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "WJ thread #" + mCount.getAndIncrement());
        }
    };

    private static final ThreadFactory IO_THREAD_FACTORY = new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "WJ io thread");
        }
    };

    private static final ThreadFactory SCHEDULED_THREAD_FACTORY = new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "WJ schedule thread");
        }
    };

    private final Executor mCachePool;
    private final Executor mMainThread;
    private final ScheduledExecutorService mScheduledPool;
    private final ExecutorService mIoThread;

    private TaskExecutors(Executor cachePool,
                          Executor mainThread,
                          ScheduledExecutorService scheduledPool,
                          ExecutorService ioThread
    ) {
        mCachePool = cachePool;
        mMainThread = mainThread;
        mScheduledPool = scheduledPool;
        mIoThread = ioThread;
    }

    private TaskExecutors() {
        this(Executors.newCachedThreadPool(CACHE_THREAD_FACTORY),
                new MainThreadExecutor(),
                Executors.newScheduledThreadPool(CORE_SIZE, SCHEDULED_THREAD_FACTORY),
                Executors.newSingleThreadExecutor(IO_THREAD_FACTORY));
    }

    public static TaskExecutors getInstance() {
        return Inner.sTaskExecutors;
    }

    public Executor getCachePool() {
        return mCachePool;
    }

    public Executor getMainThread() {
        return mMainThread;
    }

    public ScheduledExecutorService getScheduledThread() {
        return mScheduledPool;
    }

    public ExecutorService getIoThread() {
        return mIoThread;
    }

    private static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    private static class Inner {
        private final static TaskExecutors sTaskExecutors = new TaskExecutors();
    }
}
