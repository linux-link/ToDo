package com.wujia.splash

import android.app.Application
import com.miui.zeus.mimo.sdk.MimoSdk
import com.wujia.todo.ct.base.BaseApplication

const val MI_APP_ID = "2882303761517411490"

class SplashApplication : BaseApplication(){

    override fun onCreate() {
        super.onCreate()
        initModuleApp(this)
        initModuleData(this)

    }

    override fun initModuleApp(application: Application) {
        closeAndroidPDialog()
        initAd(application)
    }

    override fun initModuleData(application: Application) {
        // do nothing
    }

    private fun initAd(application: Application) {
        // MI
        MimoSdk.init(application, MI_APP_ID)
        // 以下为调试开关，上线需关闭，默认均为false
        MimoSdk.setDebugOn(true) // 设置sdk 是否打开debug
        MimoSdk.setStagingOn(true); // 设置sdk是否打开测试环境
    }

    private fun closeAndroidPDialog() {
        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod =
                cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val mHiddenApiWarningShown =
                cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.isAccessible = true
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}