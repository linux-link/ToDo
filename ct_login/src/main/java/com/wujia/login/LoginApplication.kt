package com.wujia.login

import android.app.Application
import com.wujia.todo.ct.base.BaseApplication

class LoginApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        initModuleApp(this)
        initModuleData(this)
    }

    override fun initModuleApp(application: Application) {

    }

    override fun initModuleData(application: Application) {
    }
}