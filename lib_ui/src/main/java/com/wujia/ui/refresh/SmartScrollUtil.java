package com.wujia.ui.refresh;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SmartScrollUtil {

    public static boolean isChildScrolled(@NonNull View childView) {
        if (childView instanceof AdapterView) {
            if (((AdapterView) childView).getFirstVisiblePosition() != 0
                    || ((AdapterView) childView).getFirstVisiblePosition() == 0
                    && ((AdapterView) childView).getChildAt(0) != null
                    && ((AdapterView) childView).getChildAt(0).getTop() < 0) {
                return true;
            }
        } else if (childView.getScrollY() > 0) {
            return true;
        }

        if (childView instanceof RecyclerView) {
            View view = ((RecyclerView) childView).getChildAt(0);
            int firstPosition = ((RecyclerView) childView).getChildAdapterPosition(view);
            return firstPosition != 0 || view.getTop() != 0;
        }
        return false;
    }

    private static View findScrollableChild(@NonNull ViewGroup viewGroup) {
        View child = viewGroup.getChildAt(1);
        if (child instanceof RecyclerView || child instanceof AdapterView) {
            return child;
        }
        if (child instanceof ViewGroup) {
            View tempChild = ((ViewGroup) child).getChildAt(0);
            if (tempChild instanceof RecyclerView || tempChild instanceof AdapterView) {
                child = tempChild;
            }
        }
        return child;
    }

}
