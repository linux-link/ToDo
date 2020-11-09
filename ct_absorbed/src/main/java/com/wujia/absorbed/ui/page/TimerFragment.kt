package com.wujia.absorbed.ui.page

import android.os.Bundle
import com.wujia.absorbed.R
import com.wujia.absorbed.databinding.AbFragmentTimerBinding
import com.wujia.absorbed.viewmodel.TimerViewModel
import com.wujia.arch.mvvm.BaseMvvmFragment

/**
 * 定时器.
 * @author WuJia.
 * @date 2020/11/9
 * @version 1.0
 */
class TimerFragment : BaseMvvmFragment<TimerViewModel, AbFragmentTimerBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() =
            TimerFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun setLayout(): Int {
        return R.layout.ab_fragment_timer
    }

    override fun initView() {

    }

    override fun initViewObservable(viewModel: TimerViewModel?) {

    }

    override fun loadData(viewModel: TimerViewModel?) {

    }
}