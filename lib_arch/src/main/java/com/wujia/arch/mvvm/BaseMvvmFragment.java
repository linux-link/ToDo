package com.wujia.arch.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
 * @desc Base fragment with mvvm adn databinding
 */
public abstract class BaseMvvmFragment<M extends BaseViewModel, V extends ViewDataBinding>
        extends BaseFragment {

    private V mBinding;
    private M mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, setLayout(), container, false);
        mBinding.setLifecycleOwner(this);
        initViewModel();
        initView();
        initViewObservable(mViewModel);
        mRootView = mBinding.getRoot();
        return mRootView;
    }

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData(mViewModel);
    }

    protected abstract void loadData(M viewModel);

    protected V getBinding() {
        return mBinding;
    }

    protected M getViewModel() {
        return mViewModel;
    }
}
