package com.wujia.todo.main

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.wujia.todo.ct.base.BaseApplication

class MainApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
    }

    override fun initModuleApp(application: Application) {

    }

    override fun initModuleData(application: Application) {


    }

}