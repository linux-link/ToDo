package com.wujia.ui.tabs;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public interface ISmartTabLayout<Tab extends ViewGroup, Data> {

    Tab findTab(@NonNull Data data);

    void defaultSelected(@NonNull Data defaultInfo);

    void inflateInfo(@NonNull List<Data> infoList);

    void addTabSelectedChangeListener(OnTabSelectedListener<Data> listener);

    interface OnTabSelectedListener<Data> {
        void onTabSelectedChange(int index, @Nullable Data prevInfo, @NonNull Data nextInfo);
    }
}
