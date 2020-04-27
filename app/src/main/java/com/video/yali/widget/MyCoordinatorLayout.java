package com.video.yali.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.blankj.utilcode.util.LogUtils;
import com.video.yali.listener.CoordinatorListener;
import com.video.yali.listener.ScrollViewListener;

public class MyCoordinatorLayout extends CoordinatorLayout {

    private CoordinatorListener scrollViewListener = null;

    public MyCoordinatorLayout(@NonNull Context context) {
        super(context);
    }

    public MyCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollViewListener(CoordinatorListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        LogUtils.i("onscrollchanged");
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
