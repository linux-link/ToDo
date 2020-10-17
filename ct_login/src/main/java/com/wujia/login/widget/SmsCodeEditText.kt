package com.wujia.login.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText


class SmsCodeEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {

    private var lastTime: Long = 0

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        this.text?.let {
            this.setSelection(it.length)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //屏蔽双击事件
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastTime < 500) {
                    lastTime = currentTime
                    return true
                } else {
                    lastTime = currentTime
                }
            }
        }
        return super.onTouchEvent(event)
    }

}