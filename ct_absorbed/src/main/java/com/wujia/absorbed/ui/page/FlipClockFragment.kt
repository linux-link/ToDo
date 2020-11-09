package com.wujia.absorbed.ui.page

import com.wujia.absorbed.R
import com.wujia.absorbed.databinding.AbFragmentFlipClockBinding
import com.wujia.absorbed.viewmodel.FlipClockViewModel
import com.wujia.arch.mvvm.BaseMvvmFragment

/**
 * 翻页时钟.
 * @author WuJia.
 * @date 2020/11/9
 * @version 1.0
 */
class FlipClockFragment : BaseMvvmFragment<FlipClockViewModel, AbFragmentFlipClockBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() =
            FlipClockFragment().apply {

            }
    }

    override fun setLayout(): Int {
        return R.layout.ab_fragment_flip_clock
    }

    override fun initView() {

    }

    override fun initViewObservable(viewModel: FlipClockViewModel?) {

    }

    override fun loadData(viewModel: FlipClockViewModel?) {

    }

}