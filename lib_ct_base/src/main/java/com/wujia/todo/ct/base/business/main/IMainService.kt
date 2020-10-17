package com.wujia.todo.ct.base.business.main

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @author WJ.
 * @date 2020/7/11 23:26
 *
 * @desc 主模块对外暴露的接口
 */
interface IMainService : IProvider {

    fun startMain(context: Context)

}