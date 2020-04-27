package com.video.yali.listener;

import com.video.yali.widget.MyCoordinatorLayout;
import com.video.yali.widget.ObservableScrollView;

public interface CoordinatorListener {

    void onScrollChanged(MyCoordinatorLayout scrollView, int x, int y, int oldx, int oldy);
}
