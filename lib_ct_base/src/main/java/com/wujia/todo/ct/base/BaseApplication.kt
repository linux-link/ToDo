package com.wujia.todo.ct.base

import android.app.Application
//import cn.bmob.v3.Bmob
//import cn.bmob.v3.BmobConfig
import com.wujia.arch.AbsApplication

abstract class BaseApplication : AbsApplication() {

    companion object {
        val moduleApps = arrayOf(
            "com.wujia.splash.SplashApplication",
            "com.wujia.login.LoginApplication"
        )
    }

    override fun onCreate() {
        super.onCreate()
        initBmob(this)
    }

    private fun initBmob(application: Application) {
//        val config: BmobConfig = BmobConfig.Builder(application)
//            .setApplicationId(BMOB_APPLICATION_ID)
//            .setConnectTimeout(BMOB_CONNECT_TIME_OUT)
//            .build()
//        Bmob.initialize(config)
    }

    abstract fun initModuleApp(application: Application)

    abstract fun initModuleData(application: Application)
}