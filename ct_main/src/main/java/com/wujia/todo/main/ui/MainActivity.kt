package com.wujia.todo.main.ui

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.wujia.arch.eventbus.LiveDataBus
import com.wujia.arch.mvvm.BaseMvvmActivity
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
        supportFragmentManager.beginTransaction().replace(
            R.id.nav_container, TodayFragment.newInstance()
        ).commit()
        initListener()
    }

    private fun initListener() {
        binding.navLayout.setClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            when (it.id) {
                R.id.btn_absorbed -> {
                    transaction.replace(
                        R.id.nav_container, AbsorbedFragment.newInstance()
                    ).commit()
                }
                R.id.btn_today -> {
                    transaction.replace(
                        R.id.nav_container, TodayFragment.newInstance()
                    ).commit()
                }
            }
            binding.drawerLayout.close()
        }
    }

    override fun initViewObservable(viewModel: MainViewModel?) {
        LiveDataBus.getInstance().getChannel(R.id.drawer_layout.toString())
            .observe(this, object : Observer<Boolean> {
                override fun onChanged(state: Boolean) {
                    if (state) {
                        binding.drawerLayout.openDrawer(GravityCompat.START)
                    } else {
                        binding.drawerLayout.close()
                    }
                }
            })

        LiveDataBus.getInstance().getChannel(R.id.nav_container.toString())
            .observe(this, object : Observer<Int> {
                override fun onChanged(id: Int?) {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.nav_container, TodayFragment.newInstance()
                    ).commit()
                }
            })
    }

    override fun loadData(viewModel: MainViewModel?) {
    }
}