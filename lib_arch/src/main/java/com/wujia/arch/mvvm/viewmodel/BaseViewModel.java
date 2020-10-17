package com.wujia.arch.mvvm.viewmodel;

import androidx.lifecycle.ViewModel;

import com.wujia.arch.mvvm.model.BaseRepository;

/**
 * Base viewModel.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/9/8
 */
public abstract class BaseViewModel<M extends BaseRepository> extends ViewModel {

    protected final M mRepository;

    public BaseViewModel() {
        mRepository = initRepository();
    }

    protected abstract M initRepository();

    public M getRepository() {
        return mRepository;
    }
}
