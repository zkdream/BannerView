package com.zk.test.bannerview;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * @author Created by zhangke
 * @filename BannerScroller
 * @date on 2017\11\28 0028 16:30
 * @email 206357792@qq.com
 * @describe 改变ViewPager切换的速率
 */

public class BannerScroller extends Scroller{

    //    改变ViewPager切换的速率 动画的持续时间
    private int mScrollerDuration=950;

    public void setScrollerDuration(int scrollerDuration) {
        this.mScrollerDuration = scrollerDuration;
    }
    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy,mScrollerDuration);
    }
}
