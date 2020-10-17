package com.wujia.arch.mvvm;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;


import com.wujia.arch.mvvm.viewmodel.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author wujia0916.
 * @date 20-3-10
 * @desc Base activity with mvvm and databinding
 */
public abstract class BaseMvvmActivity<M extends BaseViewModel, V extends ViewDataBinding>
        extends BaseActivity {

    private V mBinding;
    private M mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, setLayout());
        initView(mBinding);
        initViewModel();
        initViewObservable(mViewModel);
        loadData(mViewModel);
    }

    protected abstract @LayoutRes int setLayout();

    protected abstract void initView(V binding);

    protected void initViewModel() {
        Class<M> modelClass;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class<M>) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            modelClass = (Class<M>) BaseViewModel.class;
        }
        mViewModel = createViewModel(this, modelClass);
        if (mViewModel == null) {
            mViewModel = new ViewModelProvider(this,
                    new ViewModelProvider.NewInstanceFactory()).get(modelClass);
        }
    }

    protected <T extends ViewModel> T createViewModel(ViewModelStoreOwner storeOwner,
                                                      Class<T> clazz) {
        if (createViewModelFactory() == null) {
            return null;
        }
        return new ViewModelProvider(storeOwner, createViewModelFactory()).get(clazz);
    }

    protected ViewModelProvider.Factory createViewModelFactory() {
        return null;
    }

    protected abstract void initViewObservable(M viewModel);

    protected abstract void loadData(M viewModel);

    public V getBinding() {
        return mBinding;
    }

    public M getViewModel() {
        return mViewModel;
    }
}
