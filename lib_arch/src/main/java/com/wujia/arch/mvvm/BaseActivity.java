package com.wujia.arch.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.FragmentActivity;

/**
 * @author wujia0916.
 * @date 20-3-10
 * @desc
 */
public abstract class BaseActivity extends AppCompatActivity {

    public void startActivity(Class<FragmentActivity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    public void startActivity(Class<FragmentActivity> activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * Start container activity.
     *
     * @param canonicalName canonical name
     * @param bundle        bundle
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }

    /**
     * Start activity with animation.
     *
     * @param sharedElement     shared element
     * @param sharedElementName shared element name
     * @param activityClass     activity
     * @param bundle            bundle
     */
    public void startSceneTransitionActivity(@NonNull View sharedElement,
                                             @NonNull String sharedElementName,
                                             Class<FragmentActivity> activityClass,
                                             Bundle bundle) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, sharedElement, sharedElementName
        );
        Intent intent = new Intent(this, activityClass);
        intent.putExtras(bundle);
        startActivity(intent, compat.toBundle());
    }
}
