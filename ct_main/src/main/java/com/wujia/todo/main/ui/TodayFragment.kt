package com.wujia.todo.main.ui

import android.os.Bundle
import android.view.View
import com.wujia.arch.utils.eventbus.LiveDataBus
import com.wujia.arch.mvvm.BaseMvvmFragment
import com.wujia.ui.popup.PPWindow
import com.wujia.todo.ct.base.ACTION_DRAWER_LAYOUT
import com.wujia.todo.main.R
import com.wujia.todo.main.databinding.MainFragmentTodayPagerBinding
import com.wujia.todo.main.viewmodel.TodayViewModel

/**
 * Today pager.
 *
 * @author WuJia.
 * @date 2020/10/31
 * @version 1.0
 */
class TodayFragment : BaseMvvmFragment<TodayViewModel, MainFragmentTodayPagerBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() =
            TodayFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun initView() {
        initActionBar()
    }

    private fun initActionBar() {
        binding.actionBar.toolbarTitle.text = "今天"
        binding.actionBar.toolbarLeftIcon.setImageResource(R.drawable.ic_menu_24)
        binding.actionBar.toolbarRightIcon.visibility = View.VISIBLE
        binding.actionBar.toolbarRightIcon.setImageResource(R.drawable.ic_more_vert_black)
        binding.actionBar.setClickListener {
            when (it.id) {
                R.id.toolbar_left_icon->{
                    LiveDataBus.getInstance().getChannel(ACTION_DRAWER_LAYOUT)
                        .value = true
                }
                R.id.toolbar_right_icon -> {
                    PPWindow.Builder()
                        .setView(R.layout.main_menu)
                        .setCoverAnchor(true)
                        .build(requireContext())
                        .showAsDropDown(binding.actionBar.toolbarRightIcon)
                }
            }
        }
    }

    override fun initViewObservable(viewModel: TodayViewModel?) {

    }

    override fun loadData(viewModel: TodayViewModel?) {

    }

    override fun setLayout(): Int {
        return R.layout.main_fragment_today_pager
    }

}