package com.wujia.absorbed.ui.page

import android.os.Bundle
import com.wujia.absorbed.R
import com.wujia.absorbed.databinding.AbFragmentCompassBinding
import com.wujia.absorbed.viewmodel.CompassViewModel
import com.wujia.arch.mvvm.BaseMvvmFragment

/**
 * 罗盘时钟.
 * @author WuJia.
 * @date 2020/11/9
 * @version 1.0
 */
class CompassFragment : BaseMvvmFragment<CompassViewModel, AbFragmentCompassBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() =
            CompassFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun setLayout(): Int {
        return R.layout.ab_fragment_compass
    }

    override fun initView() {

    }

    override fun initViewObservable(viewModel: CompassViewModel?) {

    }

    override fun loadData(viewModel: CompassViewModel?) {

    }

}