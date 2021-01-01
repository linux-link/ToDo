package com.wujia.ui.refresh;

public interface ISmartRefresh {

    void setDisableRefreshOnScroll(boolean disableRefreshScroll);

    void onRefreshFinished();

    void setRefreshListener(SmartRefreshListener listener);

    void setRefreshOverView(SmartOverView overView);

    interface SmartRefreshListener {

        void onRefreshing();

        boolean enableRefresh();
    }
}
