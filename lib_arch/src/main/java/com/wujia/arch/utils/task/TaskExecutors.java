package com.wujia.arch.utils.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread pool.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/10/13
 */
public final class TaskExecutors {

    public static final String TAG = TaskExecutors.class.getSimpleName();
    private static final TaskExecutors TASK_EXECUTORS = new TaskExecutors();

    private boolean mIsPause;
    private final ThreadPoolExecutor mPoolExecutor;
    private final ReentrantLock mLock = new ReentrantLock();
    private final Condition mPauseCondition;
    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    public static TaskExecutors getInstance() {
        return TASK_EXECUTORS;
    }

    private TaskExecutors() {
        mPauseCondition = mLock.newCondition();
        int cpuCount = Runtime.getRuntime().availableProcessors();
        int corePoolSize = cpuCount + 1;
        int maxPoolSize = cpuCount * 2 + 1;
        PriorityBlockingQueue<Runnable> blockingQueue = new PriorityBlockingQueue<>();
        long keepAliveTime = 30L;

        AtomicLong seq = new AtomicLong();

        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("TASK#" + seq.getAndIncrement());
                return thread;
            }
        };

        mPoolExecutor = new ThreadPoolExecutor(
                corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, blockingQueue, factory) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                super.beforeExecute(t, r);
                if (mIsPause) {
                    mLock.lock();
                    try {
                        mPauseCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        mLock.unlock();
                    }
                }
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                Log.d(TAG, "this thread priority is " + ((PriorityRunnable) r).mPriority);
            }
        };
    }

    public void execute(@IntRange(from = 0, to = 10) int priority, Runnable runnable) {
        mPoolExecutor.execute(new PriorityRunnable(priority, runnable));
    }

    public void mainPost(Runnable runnable) {
        mMainHandler.post(runnable);
    }

    public void mainPostDelay(Runnable runnable, long delayMills) {
        mMainHandler.postDelayed(runnable, delayMills);
    }

    public void mainSendAtFrontOfQueue(Runnable runnable) {
        Message message = Message.obtain(mMainHandler, runnable);
        mMainHandler.sendMessageAtFrontOfQueue(message);
    }

    public void mainRemove(Runnable runnable) {
        mMainHandler.removeCallbacks(runnable);
    }

    public void pause() {
        mLock.lock();
        try {
            mIsPause = true;
            Log.d(TAG, "thread pool is paused");
        } finally {
            mLock.unlock();
        }
    }

    public void resume() {
        mLock.lock();
        try {
            mIsPause = false;
            mPauseCondition.signalAll();
        } finally {
            mLock.unlock();
        }
        Log.d(TAG, "thread pool is resumed");
    }

    public abstract class Callable<T> implements Runnable {

        @Override
        public void run() {
            mMainHandler.post(this::onPrepare);
            T t = onBackground();
            mMainHandler.removeCallbacksAndMessages(null);
            mMainHandler.post(() -> onCompleted(t));
        }

        protected void onPrepare() {

        }

        protected abstract T onBackground();

        protected abstract void onCompleted(T t);
    }

    public static class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {

        private final int mPriority;
        private final Runnable mRunnable;

        public PriorityRunnable(int priority, Runnable runnable) {
            mPriority = priority;
            mRunnable = runnable;
        }

        @Override
        public int compareTo(PriorityRunnable runnable) {
            return Integer.compare(runnable.mPriority, this.mPriority);
        }

        @Override
        public void run() {
            mRunnable.run();
        }
    }
}
