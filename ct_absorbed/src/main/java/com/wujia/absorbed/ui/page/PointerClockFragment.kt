package com.wujia.absorbed.ui.page

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.wujia.absorbed.R
import com.wujia.absorbed.databinding.AbFragmentPointerClockBinding
import com.wujia.absorbed.viewmodel.PointerClockViewModel
import com.wujia.arch.mvvm.BaseMvvmFragment

/**
 * 指针时钟.
 * @author WuJia.
 * @date 2020/11/9
 * @version 1.0
 */
class PointerClockFragment : BaseMvvmFragment<PointerClockViewModel, AbFragmentPointerClockBinding>() {

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
        val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

            }
        }
    }

    override fun initViewObservable(viewModel: PointerClockViewModel?) {

    }

    override fun loadData(viewModel: PointerClockViewModel?) {

    }

}