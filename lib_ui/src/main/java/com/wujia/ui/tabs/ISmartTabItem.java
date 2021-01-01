package com.wujia.ui.tabs;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

public interface ISmartTabItem<Data> extends ISmartTabLayout.OnTabSelectedListener<Data> {

    void setTabInfo(@NonNull Data data);

    void resetHeight(@Px int height);

}
