package com.wujia.todo.main.viewmodel;

import com.wujia.arch.mvvm.viewmodel.BaseViewModel;
import com.wujia.todo.main.data.MainRepository;

public class MainViewModel extends BaseViewModel<MainRepository> {

    @Override
    protected MainRepository initRepository() {
        return null;
    }
}
