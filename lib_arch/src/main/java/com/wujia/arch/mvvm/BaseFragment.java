package com.wujia.arch.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * @author wujia0916.
 * @date 20-3-10
 * @desc base fragment
 */
public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayout(), container, false);
        initView();
        return mRootView;
    }

    protected abstract void initView();

    protected abstract int setLayout();

    protected void startActivity(Class<FragmentActivity> activityClass) {
        Intent intent = new Intent(requireActivity(), activityClass);
        startActivity(intent);
    }

    protected void startActivity(Class<FragmentActivity> activityClass, Bundle bundle) {
        Intent intent = new Intent(requireActivity(), activityClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    protected void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(requireContext(), ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }
}
