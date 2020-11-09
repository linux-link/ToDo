package com.wujia.absorbed.viewmodel

import com.wujia.absorbed.data.AbsorbedRepository
import com.wujia.arch.mvvm.viewmodel.BaseViewModel

class TimerViewModel : BaseViewModel<AbsorbedRepository>() {
    override fun initRepository(): AbsorbedRepository {
        return AbsorbedRepository()
    }
}