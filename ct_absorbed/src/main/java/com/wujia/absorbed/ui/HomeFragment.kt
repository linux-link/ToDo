package com.wujia.absorbed.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.wujia.absorbed.R
import com.wujia.absorbed.databinding.AbFragmentAbsorbedPagerBinding
import com.wujia.absorbed.ui.page.CompassFragment
import com.wujia.absorbed.ui.page.FlipClockFragment
import com.wujia.absorbed.ui.page.PointerClockFragment
import com.wujia.absorbed.ui.page.TimerFragment
import com.wujia.absorbed.viewmodel.AbsorbedViewModel
import com.wujia.arch.utils.eventbus.LiveDataBus
import com.wujia.arch.mvvm.BaseMvvmFragment
import com.wujia.resource.dialog.MaterialDialogConfig
import com.wujia.resource.popup.PPWindow
import com.wujia.todo.ct.base.ACTION_DRAWER_LAYOUT
import com.wujia.todo.ct.base.ROUTER_ABSORBED

/**
 * 专注页面.
 *
 * @author WuJia.
 * @date 2020/10/31
 * @version 1.0
 */
@Route(path = ROUTER_ABSORBED)
class HomeFragment : BaseMvvmFragment<AbsorbedViewModel, AbFragmentAbsorbedPagerBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun setLayout(): Int {
        return R.layout.ab_fragment_absorbed_pager
    }

    override fun initView() {
        initActionBar()

        val list = arrayListOf(
            PointerClockFragment.newInstance(),
            FlipClockFragment.newInstance(),
            CompassFragment.newInstance(),
            TimerFragment.newInstance()
        )

        binding.viewPager.adapter = object : FragmentStateAdapter(childFragmentManager, lifecycle) {

            override fun getItemCount(): Int = list.size

            override fun createFragment(position: Int): Fragment = list[position]

        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }
        })
    }

    private fun initActionBar() {
        binding.actionBar.toolbarTitle.text = "专注"
        binding.actionBar.toolbarLeftIcon.setImageResource(R.drawable.ic_menu_24)
        binding.actionBar.toolbarRightIcon.visibility = View.VISIBLE
        binding.actionBar.toolbarRightIcon.setImageResource(R.drawable.ic_more_vert_black)
        binding.actionBar.setClickListener {
            when (it.id) {
                R.id.toolbar_left_icon -> {
                    LiveDataBus.getInstance().getChannel(ACTION_DRAWER_LAYOUT)
                        .value = true
                }
                R.id.toolbar_right_icon -> {
                    PPWindow.Builder()
                        .setView(R.layout.ab_popup_menu)
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