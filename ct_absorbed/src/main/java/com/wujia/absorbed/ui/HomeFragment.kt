package com.wujia.absorbed.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.wujia.absorbed.R
import com.wujia.absorbed.databinding.AbFragmentAbsorbedPagerBinding
import com.wujia.absorbed.ui.page.CompassFragment
import com.wujia.absorbed.ui.page.FlipClockFragment
import com.wujia.absorbed.ui.page.PointerClockFragment
import com.wujia.absorbed.ui.page.TimerFragment
import com.wujia.absorbed.viewmodel.AbsorbedViewModel
import com.wujia.arch.eventbus.LiveDataBus
import com.wujia.arch.mvvm.BaseMvvmFragment
import com.wujia.resource.popup.PPWindow
import com.wujia.todo.ct.base.ACTION_DRAWER_LAYOUT

/**
 * 专注页面.
 *
 * @author WuJia.
 * @date 2020/10/31
 * @version 1.0
 */
class HomeFragment : BaseMvvmFragment<AbsorbedViewModel, AbFragmentAbsorbedPagerBinding>() {

    private var currentPosition = 0;

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
                currentPosition = position
            }
        })

        binding.setClickListener {
            when (it.id) {
                R.id.btn_next -> {
                    if (currentPosition <= list.size - 1) {
                        val pos = currentPosition + 1
                        binding.viewPager.setCurrentItem(pos, true)
                    }
                }
                R.id.btn_previous -> {
                    if (currentPosition <= list.size - 1 && currentPosition > 0) {
                        val pos = currentPosition - 1
                        binding.viewPager.setCurrentItem(pos, true)
                    }
                }
            }
        }
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