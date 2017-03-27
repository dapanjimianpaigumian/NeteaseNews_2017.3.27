package com.yulu.zhaoxinpeng.neteasenews.adapter;

import android.app.Application;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yulu.zhaoxinpeng.neteasenews.activity.ViewPagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public class GuidanceAdapter extends PagerAdapter{
    public List<View> list=new ArrayList<View>();



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    public void setDataList(List<View> list) {
        this.list=list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 把要显示的数据添加到ViewPager中
        container.addView(list.get(position));

        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
}
