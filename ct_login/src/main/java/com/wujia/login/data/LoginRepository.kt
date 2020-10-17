package com.wujia.login.data

import cn.bmob.v3.BmobSMS
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import com.wujia.arch.mvvm.model.BaseRepository
import com.wujia.todo.ct.base.BMOB_SMS_TEMP


object LoginRepository : BaseRepository() {

    fun getSmsCode(phoneNumber: String, listener: QueryListener<Int>) {
        BmobSMS.requestSMSCode(phoneNumber, BMOB_SMS_TEMP, listener)
    }

    fun verifySmsCode(smsCode: String, phoneNumber: String, listener: UpdateListener) {
        BmobSMS.verifySmsCode(phoneNumber, smsCode, listener)
    }

}