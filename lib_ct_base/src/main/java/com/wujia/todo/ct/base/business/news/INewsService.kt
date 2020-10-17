package com.wujia.todo.ct.base.business.news

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * Home模块对外
 * @author WuJia.
 * @date 2020/9/11
 * @version 1.0
 */
interface INewsService : IProvider {

    fun getNewsFragment(): Fragment

}