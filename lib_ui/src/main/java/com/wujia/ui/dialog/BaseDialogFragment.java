package com.wujia.ui.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;

/**
 * Base dialog fragment.
 *
 * @author WuJia.
 * @version 1.0
 * @date 20-9-2
 */
public abstract class BaseDialogFragment<V extends ViewDataBinding> extends DialogFragment {

    protected V mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil
                .inflate(inflater, setLayout(), container, false);
        initView(mBinding);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogBackground();
        setDialogWindow();
    }

    /**
     * Set dialog background to transparent.
     */
    private void setDialogBackground() {
        DisplayMetrics dm = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(dm.widthPixels, window.getAttributes().height);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    /**
     * Set dialog window size.
     */
    private void setDialogWindow() {
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) setDialogWidth();
        params.height = (int) setDialogHeight();
        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
    }

    protected abstract void initView(V binding);

    protected abstract int setLayout();

    protected abstract float setDialogHeight();

    protected abstract float setDialogWidth();

    public V getBinding() {
        return mBinding;
    }
}
