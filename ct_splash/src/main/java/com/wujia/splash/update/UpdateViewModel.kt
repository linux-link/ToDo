package com.wujia.splash.update

import com.wujia.arch.mvvm.viewmodel.BaseViewModel
import com.wujia.splash.data.SplashRepository

class UpdateViewModel : BaseViewModel<SplashRepository>() {

    override fun initRepository(): SplashRepository {
        return SplashRepository()
    }
}