package com.wujia.login.ui

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelStore
import com.wujia.arch.mvvm.BaseMvvmActivity
import com.wujia.login.R
import com.wujia.login.databinding.LgActivitySmsCodeBinding
import com.wujia.login.utils.LoginViewModelStore
import com.wujia.login.widget.SmsCodeView
import com.wujia.ui.button.StateButton
import com.wujia.todo.ct.base.utils.CountDownTimerExt

/**
 * @author WJ.
 * @date 2020/7/11 21:11
 *
 * @desc 验证码校验
 */
class SmsCodeActivity :
    BaseMvvmActivity<LoginViewModel, LgActivitySmsCodeBinding>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SmsCodeActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView(binding: LgActivitySmsCodeBinding) {
        binding.viewModel = viewModel
        initClickListener(binding)
        startCountDown()
        binding.lgBtnConfirm.action = StateButton.ACTION_DISABLE
        binding.lgEtSms.setInputCompleteListener(object : SmsCodeView.InputCompleteListener {
            override fun inputComplete() {
                val smsCode = binding.lgEtSms.editContent
                smsCode?.let {
                    viewModel.verifySmsCode(it)
                }
            }

            override fun invalidContent() {
                // do nothing
            }
        })
    }

    private fun initClickListener(binding: LgActivitySmsCodeBinding) {
        binding.setClickListener { view ->
            when (view.id) {
                R.id.lg_btn_confirm -> {
                    viewModel.getSmsCode()
                    startCountDown()
                }
                R.id.toolbar_left_icon -> {
                    onBackPressed()
                }
            }
        }
    }

    private fun startCountDown() {
        CountDownTimerExt.startCountDown(60000, object : CountDownTimerExt.IOnFinishListener {
            override fun onFinnish() {
                binding.lgBtnConfirm.action = StateButton.ACTION_NORMAL
                binding.lgBtnConfirm.text = resources.getString(R.string.lg_login_btn_text)
            }

            override fun onTick(second: Long) {
                val time: Int = (second / 1000).toInt()
                binding.lgBtnConfirm.text =
                    "$time${resources.getString(R.string.lg_login_code_hint)}"
                binding.lgBtnConfirm.action = StateButton.ACTION_DISABLE
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        CountDownTimerExt.cancelCount()
    }

    override fun loadData(viewModel: LoginViewModel) {
        // do nothing
    }

    override fun getViewModelStore(): ViewModelStore {
        return LoginViewModelStore.loginViewModelStore
    }

    override fun setLayout(): Int {
        return R.layout.lg_activity_sms_code
    }

    override fun initViewObservable(viewModel: LoginViewModel?) {

    }

}