package com.example.sy.mycycleview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by sy on 2016/5/24.
 */
public class CycleViewPager extends ViewPager{
    private Context context;
    private InnerPagerAdapter mAdapter;
    public CycleViewPager(Context context) {
        super(context);
        setOnPageChangeListener(null);
    }

    public CycleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnPageChangeListener(null);
        this.context = context;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mAdapter = new InnerPagerAdapter(adapter);
        super.setAdapter(mAdapter);
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        super.setOnPageChangeListener(new InnerOnPageChangeListener(listener));
    }

    private class InnerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        private int position;
        private OnPageChangeListener onPageChangeListener;

        public InnerOnPageChangeListener(OnPageChangeListener onPageChangeListener){
            this.onPageChangeListener = onPageChangeListener;
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //Log.i("position",""+position);
            onPageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            if (onPageChangeListener != null)
                onPageChangeListener.onPageSelected(position);
            //滑动结束后的位置position
            this.position = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (onPageChangeListener != null)
                onPageChangeListener.onPageScrollStateChanged(state);
            //发生滑动之后的回调
            //如果滑动之后的postion为0即{d,a,b,c,d,a}中的d则将当前item设置为d即左滑到最后一张图d
            //如果滑动之后positon为最后一个元素及{d,a,b,c,d,a}中的a则将当前的item设置为第一张图a
            if(state == ViewPager.SCROLL_STATE_IDLE) {
                if (position == 0){
                    setCurrentItem(mAdapter.getCount()-2,false);
                }
                else if(position == mAdapter.getCount()-1){
                    setCurrentItem(1,false);
                }
            }
        }
    }



    private class InnerPagerAdapter extends PagerAdapter{

        private PagerAdapter adapter;
        private int position;

        public InnerPagerAdapter(PagerAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getCount() {
            //在原本的图片数组上扩展两个图片，用于来应用边界滑动
            return adapter.getCount()+2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //将图片数组{a,b,c,d}扩展为{d,a,b,c,d,a}方便来进行边界滑动
            if (position == 0){
                position = adapter.getCount()-1;
            }
            else if(position == adapter.getCount()+1){
                position = 0;
            }else{
                position -=1;
            }
            this.position = position;
            return adapter.instantiateItem( container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            ImageView view = (ImageView) object;
            container.removeView(view);
        }
    }



}
