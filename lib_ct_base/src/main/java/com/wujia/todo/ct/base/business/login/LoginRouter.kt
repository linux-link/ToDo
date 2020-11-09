package com.wujia.todo.ct.base.business.login

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.wujia.todo.ct.base.business.login.model.UserEntity

const val LOGIN_ROUTER = "/login/login_pager"

/**
 * @author WJ.
 * @date 2020/7/11 23:26
 *
 * @desc 登录模块路由
 */
object LoginRouter {

    init {
        ARouter.getInstance().inject(this)
    }

    @JvmField
    @Autowired(name = LOGIN_ROUTER)
    var loginService: ILoginService? = null

    fun getUserInfo(): UserEntity {
        TODO("Not yet implemented")
    }

    fun startLogin(context: Context) {
        Log.e("TAG", "startLogin:$loginService")
        loginService?.startLogin(context)
    }

    fun hasLogin(): Boolean {
        TODO("Not yet implemented")
    }


}