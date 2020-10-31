package com.wujia.todo.main

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.view.GravityCompat
import com.wujia.arch.mvvm.BaseMvvmActivity
import com.wujia.resource.popup.PPWindow
import com.wujia.resource.utils.StatusBar
import com.wujia.todo.main.databinding.MainActivityMainBinding

/**
 * Main pager.
 *
 * @author WuJia.
 * @date 2020/10/31
 * @version 1.0
 */
class MainActivity : BaseMvvmActivity<MainViewModel, MainActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        StatusBar.fitSystemBar(this)
        StatusBar.lightStatusBar(this, true)
        super.onCreate(savedInstanceState)
    }

    override fun setLayout(): Int {
        return R.layout.main_activity_main
    }

    override fun initView(binding: MainActivityMainBinding?) {
        initActionBar()
    }

    private fun initActionBar() {
        binding.mainContainer.actionBar.toolbarLeftIcon.setImageResource(R.drawable.ic_menu_24)
        binding.mainContainer.actionBar.toolbarRightIcon.visibility = View.VISIBLE
        binding.mainContainer.actionBar.toolbarRightIcon.setImageResource(R.drawable.ic_more_vert_black)
        binding.mainContainer.actionBar.setClickListener {
            when (it.id) {
                R.id.toolbar_left_icon -> {
                    binding.drawerLayout.openDrawer(GravityCompat.START)
                }
                R.id.toolbar_right_icon -> {
                    PPWindow.Builder()
                        .setView(R.layout.main_menu)
                        .setCoverAnchor(true)
                        .build(this)
                        .showAsDropDown(binding.mainContainer.actionBar.toolbarRightIcon)
                }
            }
        }
    }

    override fun initViewObservable(viewModel: MainViewModel?) {

    }

    override fun loadData(viewModel: MainViewModel?) {

    }
}