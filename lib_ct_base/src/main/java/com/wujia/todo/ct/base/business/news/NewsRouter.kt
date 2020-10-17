package com.wujia.todo.ct.base.business.news

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter

const val NEWS_ROUTER = "/news/news_service"

/**
 * 新闻模块路由
 *
 * @author WuJia.
 * @date 2020/9/11
 * @version 1.0
 */
class NewsRouter {

    init {
        ARouter.getInstance().inject(this)
    }

    @JvmField
    @Autowired(name = NEWS_ROUTER)
    var newsService: INewsService? = null

    fun getNewsFragment(): Fragment {
        return newsService?.getNewsFragment()!!
    }


}