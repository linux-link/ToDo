package com.wujia.splash.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.miui.zeus.mimo.sdk.SplashAd
import com.wujia.splash.R
import com.wujia.todo.ct.base.business.login.LoginRouter
import kotlinx.android.synthetic.main.sp_activity_splash.*

const val XIAOMI_POSITION_ID = "94f4805a2d50ba6e853340f9035fda18"

open class SplashActivity : FragmentActivity() {

    private var splashAd: SplashAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        avoidLauncherAgain()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sp_activity_splash)
        xiaomiAd()
        //
        container.setOnClickListener {
            LoginRouter.startLogin(this)
        }
    }

    private fun avoidLauncherAgain() {
        //避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot) { // 判断当前activity是不是所在任务栈的根
            val intent = intent
            if (intent != null) {
                val action = intent.action
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                    finish()
                }
            }
        }
    }

    private fun xiaomiAd() {
        splashAd = SplashAd(this)
        splashAd?.loadAndShow(container,
            XIAOMI_POSITION_ID, object : SplashAd.SplashAdListener {
                override fun onAdRenderFailed() {
                }

                override fun onAdLoadFailed(errorCode: Int, errorMsg: String?) {
                    Log.e("TAG", "onAdLoadFailed: $errorMsg")
                }

                override fun onAdShow() {
                }

                override fun onAdDismissed() {
                    Log.e("TAG", "onAdDismissed: ")
                    LoginRouter.startLogin(this@SplashActivity)
                }

                override fun onAdClick() {
                    Log.e("TAG", "onAdClick: ")
                    LoginRouter.startLogin(this@SplashActivity)
                }

                override fun onAdLoaded() {

                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        splashAd?.destroy()
    }
}