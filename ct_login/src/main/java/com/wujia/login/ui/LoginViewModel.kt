package com.wujia.login.ui

import android.util.Log
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import com.wujia.arch.mvvm.livedata.SingleLiveEvent
import com.wujia.arch.mvvm.viewmodel.BaseViewModel
import com.wujia.login.data.LoginRepository

class LoginViewModel : BaseViewModel<LoginRepository>() {

    val smsCodeSuccess = SingleLiveEvent<Boolean>()
    val smsVerifySuccess = SingleLiveEvent<Boolean>()
    val phoneNumberLiveData = SingleLiveEvent<String>()

    fun getSmsCode() {
        phoneNumberLiveData.value?.let { phoneNumber ->
            LoginRepository.getSmsCode(phoneNumber, object : QueryListener<Int>() {
                override fun done(result: Int?, exception: BmobException?) {
                    if (exception == null) {
                        smsCodeSuccess.postValue(true)
                    } else {
                        smsCodeSuccess.postValue(false)
                        Log.e("TAG", "done: $exception")
                    }
                }
            })
        }
    }

    fun verifySmsCode(smsCode: String) {
        phoneNumberLiveData.value?.let { phone ->
            LoginRepository.verifySmsCode(smsCode, phone,
                object : UpdateListener() {
                    override fun done(exception: BmobException?) {
                        if (exception == null) {
                            updateUserInfo(phone)
                        } else {
                            Log.e("TAG", "done: $exception")
                        }
                    }
                })
        }
    }

    private fun updateUserInfo(phone: String) {
        val userEntity = BmobUser.getCurrentUser()
        userEntity.mobilePhoneNumber = phone
        userEntity.username = phone
        userEntity.mobilePhoneNumberVerified = true
        userEntity.update(object : UpdateListener() {
            override fun done(ex: BmobException?) {
                if (ex == null) {
                    smsVerifySuccess.postValue(true)
                } else {
                    smsVerifySuccess.postValue(false)
                    Log.e("TAG", "done: $ex")
                }
            }
        })
    }

    override fun initRepository(): LoginRepository {
        return LoginRepository
    }

}