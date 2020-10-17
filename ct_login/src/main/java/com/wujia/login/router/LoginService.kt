package com.wujia.login.router

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.wujia.login.ui.LoginActivity
import com.wujia.todo.ct.base.business.login.ILoginService
import com.wujia.todo.ct.base.business.login.LOGIN_ROUTER
import com.wujia.todo.ct.base.business.login.model.UserEntity

/**
 * @author WJ.
 * @date 2020/7/4 14:49
 *
 * @desc 登录模块，对外暴露的功能
 */
@Route(path = LOGIN_ROUTER)
class LoginService : ILoginService {

    override fun getUserInfo(): UserEntity {
        TODO("Not yet implemented")
    }

    override fun startLogin(context: Context) {
        LoginActivity.start(context)
    }

    override fun removeUserInfo() {
        TODO("Not yet implemented")
    }

    override fun hasLogin(): Boolean {
        return false
    }

    override fun init(context: Context?) {
        // do nothing
        Log.e("TAG", "init: LoginService")
    }
}