package com.wujia.todo.ct.base.business.login

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider
import com.wujia.todo.ct.base.business.login.model.UserEntity

/**
 * @author WJ.
 * @date 2020/7/4 14:58
 *
 * @desc 登录对外暴露的接口
 */
interface ILoginService : IProvider {

    fun getUserInfo(): UserEntity

    fun startLogin(context: Context)

    fun removeUserInfo()

    fun hasLogin(): Boolean

}