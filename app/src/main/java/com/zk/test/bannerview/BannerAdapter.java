package com.zk.test.bannerview;

import android.view.View;

/**
 * @author Created by zhangke
 * @filename BannerAdapter
 * @date on 2017\11\28 0028 16:03
 * @email 206357792@qq.com
 * @describe 轮播的基适配器
 */

public abstract class BannerAdapter {
    /**
     *根据position获取ViewPager里面的子View
     */
    public abstract View getView(int position,View contentView);

    /**
     * 获取轮播图的位置
     */
    public abstract int getCount();

    /**
     * 根据位置获取广告位置信息的描述
     */
    public String getBannerDesc(int position){
        return "";
    }
}
