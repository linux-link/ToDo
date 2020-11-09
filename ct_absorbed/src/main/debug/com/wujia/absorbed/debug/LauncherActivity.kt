package com.wujia.absorbed.debug

import android.os.Bundle
import com.wujia.absorbed.ui.HomeFragment
import com.wujia.arch.mvvm.BaseActivity

class LauncherActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startContainerActivity(HomeFragment::class.java.canonicalName)
    }

}