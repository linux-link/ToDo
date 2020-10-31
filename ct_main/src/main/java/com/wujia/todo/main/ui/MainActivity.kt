package com.wujia.todo.main.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import com.wujia.arch.mvvm.BaseMvvmActivity
import com.wujia.resource.popup.PPWindow
import com.wujia.resource.utils.StatusBar
import com.wujia.todo.main.R
import com.wujia.todo.main.databinding.MainActivityMainBinding
import com.wujia.todo.main.viewmodel.MainViewModel

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
        supportFragmentManager.beginTransaction().add(
            R.id.nav_container,TodayFragment.newInstance()
        ).commit()
    }

    override fun initViewObservable(viewModel: MainViewModel?) {

    }

    override fun loadData(viewModel: MainViewModel?) {

    }
}