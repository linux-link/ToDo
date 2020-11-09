package com.wujia.absorbed.viewmodel

import com.wujia.absorbed.data.AbsorbedRepository
import com.wujia.arch.mvvm.viewmodel.BaseViewModel

class CompassViewModel: BaseViewModel<AbsorbedRepository>(){

    override fun initRepository(): AbsorbedRepository {
        return AbsorbedRepository()
    }

}