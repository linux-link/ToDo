package com.wujia.absorbed.ui.page

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.wujia.absorbed.R
import com.wujia.absorbed.databinding.AbFragmentPointerClockBinding
import com.wujia.absorbed.viewmodel.PointerClockViewModel
import com.wujia.arch.mvvm.BaseMvvmFragment
import kotlinx.android.synthetic.main.ab_fragment_pointer_clock.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
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

    var i = 0
    var j = 0;

    override fun initView() {
        binding.pointer.autoUpdate(true)

//        val schedule: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
//        schedule.scheduleAtFixedRate({
//            i++
//            if (i % 10 == 0) {
//                j ++
//            }
//        }, 1, 1, TimeUnit.SECONDS)
    }

    override fun initViewObservable(viewModel: PointerClockViewModel?) {

    }

    override fun loadData(viewModel: PointerClockViewModel?) {

    }

}