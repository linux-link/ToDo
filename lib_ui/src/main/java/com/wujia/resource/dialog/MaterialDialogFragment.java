package com.wujia.resource.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.wujia.resource.R;
import com.wujia.resource.databinding.FragmentMaterialDialogBinding;

/**
 * General dialog.
 *
 * @author WuJia.
 * @version 1.0
 * @date 2020/9/10
 */
public class MaterialDialogFragment extends BaseDialogFragment<FragmentMaterialDialogBinding> {

    // dialog config
    private final MaterialDialogConfig mDialogConfig;

    private MaterialDialogFragment(MaterialDialogConfig config) {
        mDialogConfig = config;
    }

    /**
     * Create MaterialDialogFragment instance.
     *
     * @param config dialog config,see {@link MaterialDialogConfig}
     * @return MaterialDialogFragment
     */
    public static MaterialDialogFragment newInstance(MaterialDialogConfig config) {
        Bundle args = new Bundle();
        MaterialDialogFragment fragment = new MaterialDialogFragment(config);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(FragmentMaterialDialogBinding binding) {
        binding.setDialogConfig(mDialogConfig);
        setCancelable(mDialogConfig.isCancelable());
        initText();
        initTextColor();

        if (!mDialogConfig.isShowConfirmBtn() && !mDialogConfig.isShowCancelBtn()) {
            hideButton(binding);
        } else if (!mDialogConfig.isShowCancelBtn()) {
            hideButton(binding, true);
        } else if (!mDialogConfig.isShowConfirmBtn()) {
            this.hideButton(binding, false);
        }

        // set click listener
        final MaterialDialogConfig.IClickListener clickListener = mDialogConfig.getClickListener();
        if (clickListener != null) {
            binding.setClickListener(view -> {
                int id = view.getId();
                if (id == R.id.btn_confirm) {
                    clickListener.onConfirmClickListener(view, MaterialDialogFragment.this);
                } else if (id == R.id.btn_cancel) {
                    clickListener.onCancelClickListener(view, MaterialDialogFragment.this);
                } else {
                    // do nothing
                }
            });
        }
    }

    private void initText() {
        if (!TextUtils.isEmpty(mDialogConfig.getConfirmText())) {
            mBinding.btnConfirm.setText(mDialogConfig.getConfirmText());
        }
        if (!TextUtils.isEmpty(mDialogConfig.getCancelText())) {
            mBinding.btnCancel.setText(mDialogConfig.getCancelText());
        }
        if (!TextUtils.isEmpty(mDialogConfig.getTitleText())) {
            mBinding.tvTitle.setText(mDialogConfig.getTitleText());
        }
    }

    private void initTextColor() {
        if (mDialogConfig.getTitleTextColor() != 0) {
            mBinding.tvTitle.setTextColor(mDialogConfig.getTitleTextColor());
        }
        if (mDialogConfig.getContentTextColor() != 0) {
            mBinding.tvContent.setTextColor(mDialogConfig.getContentTextColor());
        }
        if (mDialogConfig.getCancelTextColor() != 0) {
            mBinding.btnCancel.setTextColor(mDialogConfig.getCancelTextColor());
        }
        if (mDialogConfig.getConfirmTextColor() != 0) {
            mBinding.btnConfirm.setTextColor(mDialogConfig.getConfirmTextColor());
        }
    }

    private void hideButton(FragmentMaterialDialogBinding binding) {
        binding.btnCancel.setVisibility(View.GONE);
        binding.btnConfirm.setVisibility(View.GONE);
    }

    private void hideButton(FragmentMaterialDialogBinding binding, boolean isCancelBtn) {
        if (isCancelBtn) {
            // hide cancel button
            binding.btnCancel.setVisibility(View.GONE);
        } else {
            // hide confirm button
            binding.btnConfirm.setVisibility(View.GONE);
        }
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_material_dialog;
    }

    @Override
    protected float setDialogHeight() {
        return getResources().getDimension(R.dimen.dialog_height);
    }

    @Override
    protected float setDialogWidth() {
        return getResources().getDimension(R.dimen.dialog_width);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mDialogConfig != null) {
            for (MaterialDialogConfig.IDismissListener listener
                    : mDialogConfig.getDismissListener()) {
                listener.onDismiss(MaterialDialogFragment.this);
            }
        }
    }

    public MaterialDialogConfig getDialogConfig() {
        return mDialogConfig;
    }
}
