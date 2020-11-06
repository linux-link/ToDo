package com.wujia.todo.main.ui

import android.os.Bundle
import android.view.View
import com.wujia.arch.eventbus.LiveDataBus
import com.wujia.arch.mvvm.BaseMvvmFragment
import com.wujia.resource.popup.PPWindow
import com.wujia.todo.main.R
import com.wujia.todo.main.databinding.MainFragmentAbsorbedPagerBinding
import com.wujia.todo.main.viewmodel.AbsorbedViewModel

/**
 * 专注页面.
 *
 * @author WuJia.
 * @date 2020/10/31
 * @version 1.0
 */
class AbsorbedFragment : BaseMvvmFragment<AbsorbedViewModel, MainFragmentAbsorbedPagerBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() =
            AbsorbedFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun setLayout(): Int {
        return R.layout.main_fragment_absorbed_pager
    }

    override fun initView() {
        initActionBar()
    }

    private fun initActionBar() {
        binding.actionBar.toolbarTitle.text = "专注"
        binding.actionBar.toolbarLeftIcon.setImageResource(R.drawable.ic_menu_24)
        binding.actionBar.toolbarRightIcon.visibility = View.VISIBLE
        binding.actionBar.toolbarRightIcon.setImageResource(R.drawable.ic_more_vert_black)
        binding.actionBar.setClickListener {
            when (it.id) {
                R.id.toolbar_left_icon -> {
                    LiveDataBus.getInstance().getChannel(R.id.drawer_layout.toString())
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

    override fun initViewObservable(viewModel: AbsorbedViewModel?) {

    }

    override fun loadData(viewModel: AbsorbedViewModel?) {

    }
}