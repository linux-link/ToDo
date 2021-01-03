package com.wujia.absorbed.ui.page

import android.os.Bundle
import android.os.Looper
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.View
import com.wujia.absorbed.R
import com.wujia.absorbed.databinding.AbFragmentPointerClockBinding
import com.wujia.absorbed.viewmodel.PointerClockViewModel
import com.wujia.arch.mvvm.BaseMvvmFragment
import com.wujia.arch.utils.task.TaskExecutors
import com.wujia.ui.utils.DateUtils
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * 指针时钟.
 *
 * @author WuJia.
 * @date 2020/11/9
 * @version 1.0
 */
class PointerClockFragment :
    BaseMvvmFragment<PointerClockViewModel, AbFragmentPointerClockBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() =
            PointerClockFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun setLayout(): Int {
        return R.layout.ab_fragment_pointer_clock
    }

    override fun initView() {
        initClock()
    }

    private fun initClock() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
            {
                val calendar = Calendar.getInstance()
                // 加一秒，解决显示延迟的问题
                val second = calendar[Calendar.SECOND]
                val minute = calendar[Calendar.MINUTE]
                val hour = calendar[Calendar.HOUR]

                val hourTime = SpannableStringBuilder(calendar[Calendar.HOUR_OF_DAY].toString())
                val sizeSpan = RelativeSizeSpan(2f)
                hourTime.append('h')
                    .setSpan(sizeSpan, 0, 2, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE)

                val minuteTime = SpannableStringBuilder(calendar[Calendar.MINUTE].toString())
                minuteTime.append('m')
                    .setSpan(sizeSpan, 0, 2, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE)

                val dateTime = StringBuilder()
                dateTime.append(calendar[Calendar.YEAR])
                    .append('.')
                    .append(calendar[Calendar.MONTH])
                    .append('.')
                    .append(calendar[Calendar.DAY_OF_MONTH])
                    .append(' ')
                    .append(DateUtils.getWeek(calendar[Calendar.DAY_OF_WEEK]))

                TaskExecutors.getInstance().mainPost{
                    binding.pointerClock.updateTime(hour, minute, second)
                    binding.tvHourTime.text = hourTime
                    binding.tvMinuteTime.text = minuteTime
                    binding.tvSpan.visibility = if (binding.tvSpan.visibility == View.VISIBLE
                    ) {
                        View.INVISIBLE
                    } else {
                        View.VISIBLE
                    }
                    binding.tvDateTime.text = dateTime
                }
            }, 0, 1, TimeUnit.SECONDS
        )
    }

    override fun initViewObservable(viewModel: PointerClockViewModel?) {

    }

    override fun loadData(viewModel: PointerClockViewModel?) {

    }

}