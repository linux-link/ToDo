package com.wujia.todo.ct.base.utils;

import android.os.CountDownTimer;

public class CountDownTimerExt extends CountDownTimer {

    private IOnFinishListener mListener;
    private static CountDownTimerExt mDownTimer;

    public CountDownTimerExt(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void setListener(IOnFinishListener listener) {
        mListener = listener;
    }

    @Override
    public void onTick(long second) {
        if (mListener != null) {
            mListener.onTick(second);
        }
    }

    @Override
    public void onFinish() {
        if (mListener != null) {
            mListener.onFinnish();
        }
    }

    /**
     * Start count down.
     *
     * @param millSecond millSecond
     * @param listener   IOnFinishListener
     */
    public static void startCountDown(long millSecond, IOnFinishListener listener) {
        mDownTimer = new CountDownTimerExt(millSecond, 1000);
        mDownTimer.setListener(listener);
        mDownTimer.start();
    }

    public static void cancelCount() {
        if (mDownTimer != null) {
            mDownTimer.cancel();
        }
        mDownTimer = null;
    }

    public interface IOnFinishListener {
        void onFinnish();

        void onTick(long second);
    }
}
