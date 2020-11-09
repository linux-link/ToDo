package com.wujia.todo.ct.base.business.main

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter

const val MAIN_ROUTER = "/main/main_pager"

/**
 * @author WJ.
 * @date 2020/7/11 23:27
 *
 * @desc 主模块路由
 */
object MainRouter {

    init {
        ARouter.getInstance().inject(this)
    }

    @JvmField
    @Autowired(name = MAIN_ROUTER)
    var mainService: IMainService? = null

    fun startMain(context: Context) {
        mainService?.startMain(context)
    }

}