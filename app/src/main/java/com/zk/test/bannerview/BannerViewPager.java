package com.zk.test.bannerview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Created by zhangke
 * @filename BannerViewPager
 * @date on 2017\11\28 0028 16:01
 * @email 206357792@qq.com
 * @describe 自定义的BannerViewPager
 */

public class BannerViewPager extends ViewPager{

//  自定义BannerAdapter的适配器
    private BannerAdapter mAdapter;

    private final int SCROLL_MSG=0x0011;

    private int mCutDownTime=3500;

    private BannerScroller mScroller;

    private Handler mHandler;

    private Activity mActivity;

    private List<View> mContentView;

    public BannerViewPager(Context context) {
        this(context,null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity= (Activity) context;

        try {
            Field field=ViewPager.class.getDeclaredField("mScroller");

            mScroller=new BannerScroller(context);

            field.setAccessible(true);

            field.set(this,mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mContentView=new ArrayList<>();
        initHandler();
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //   每隔*s后切换到下一页
                setCurrentItem(getCurrentItem()+1);
                // 不断循环执行
                startRoll();
            }
        };
    }

    /**
     * 设置切换页面动画的持续的时间
     * @param scrollerDuration
     */
    private void setScrollerDuration(int scrollerDuration){
        mScroller.setScrollerDuration(scrollerDuration);
    }

    public void setAdapter(BannerAdapter adapter){
        this.mAdapter=adapter;
//        设置父类ViewPager的adapter
        setAdapter(new BannerPagerAdapter());
    }

    /**
     * 实现自动轮播
     */
    public void startRoll() {
//        清除消息
        mHandler.removeMessages(SCROLL_MSG);
//       消息  延迟时间  让用户自定义  有一个默认  3500
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG,mCutDownTime);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mHandler!=null){
            mHandler.removeMessages(SCROLL_MSG);

            mActivity.getApplication().unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);

            mHandler=null;
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        if (mAdapter!=null){
            initHandler();
            startRoll();
//            管理Activity的生命周期
            mActivity.getApplication().registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        }
        super.onAttachedToWindow();
    }

    private class BannerPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

//        创建ViewPager条目回掉的方法
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View bannerItemView=mAdapter.getView(position%mAdapter.getCount(),getContentView());

            container.addView(bannerItemView);

            bannerItemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        mListener.click(position);
                    }
                }
            });

            return bannerItemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mContentView.add((View) object);
        }
    }
    /**
     * 获取服用界面
     * @return
     */
    private View getContentView() {
        for (int i = 0; i < mContentView.size();  i++) {
            if (mContentView.get(i).getParent() == null){
                return mContentView.get(i);
            }
        }
        return null;
    }
    private BannerItemClickListener mListener;

    public void setOnBannerItemClickListener(BannerItemClickListener listener){
        this.mListener=listener;
    }

    public interface BannerItemClickListener{
        public void click(int position);
    }

    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks=
            new DefaultActivityLifecycleCallbacks(){

                @Override
                public void onActivityResumed(Activity activity) {
                    // 是不是监听的当前Activity的生命周期
                    if (activity==mActivity){
                        // 开启轮播
                        mHandler.sendEmptyMessageDelayed(mCutDownTime,SCROLL_MSG);
                    }
                }

                @Override
                public void onActivityPaused(Activity activity) {
                    if (activity==mActivity){
                        // 停止轮播
                        mHandler.removeMessages(SCROLL_MSG);
                    }
                }
            };
}
