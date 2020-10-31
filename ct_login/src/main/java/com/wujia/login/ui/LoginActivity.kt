package com.wujia.login.ui

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelStore
import com.wujia.arch.mvvm.BaseMvvmActivity
import com.wujia.login.R
import com.wujia.login.databinding.LgActivityLoginBinding
import com.wujia.login.utils.LoginViewModelStore
import com.wujia.resource.button.StateButton

/**
 * @author WJ.
 * @date 2020/7/4 21:31
 *
 * @desc 登录
 */
class LoginActivity :
    BaseMvvmActivity<LoginViewModel, LgActivityLoginBinding>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView(binding: LgActivityLoginBinding) {
        initClickListener(binding)
        binding.lgBtnConfirm.action = StateButton.ACTION_DISABLE
        binding.lgEtPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                editable?.let {
                    val len = editable.trim().length
                    if (len >= 11) {
                        viewModel.phoneNumberLiveData.postValue(editable.trim().toString())
                        binding.lgBtnConfirm.action = StateButton.ACTION_NORMAL
                    } else {
                        binding.lgBtnConfirm.action = StateButton.ACTION_DISABLE
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // do nothing
            }
        })
    }

    private fun initClickListener(binding: LgActivityLoginBinding) {
        binding.clickListener = View.OnClickListener { view ->
            when (view?.id) {
                R.id.lg_btn_confirm -> {
                    val phoneNumber = binding.lgEtPhone.text?.trim().toString()
                    if (phoneNumber.isEmpty()) {
                        //TODO: show toast
                        Toast.makeText(this, "empty", Toast.LENGTH_LONG).show()
                    } else {
                        viewModel.getSmsCode()
                        SmsCodeActivity.start(this)
                    }
                }
                R.id.toolbar_left_icon -> {
                    onBackPressed()
                }
            }
        }
    }

    override fun loadData(viewModel: LoginViewModel) {
        // do nothing
    }

    override fun getViewModelStore(): ViewModelStore {
        return LoginViewModelStore.loginViewModelStore
    }

    override fun setLayout(): Int {
        return R.layout.lg_activity_login
    }

    override fun initViewObservable(viewModel: LoginViewModel?) {

    }
}
