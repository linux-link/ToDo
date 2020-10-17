package com.wujia.todo.ct.base.bean

import com.google.gson.annotations.SerializedName

data class BaseBean<T>(
    val code: Int,
    val msg: String,
    @field:SerializedName("newslist") val newsList: List<T>
)