package com.iec.dwx.timer.Adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.iec.dwx.timer.Beans.CommonBean;

import java.util.List;

/**
 * Created by Administrator on 2015/10/16 0016.
 */
public class WishDetailsPagerAdapter extends PagerAdapter {
    private final String TAG = WishDetailsPagerAdapter.class.getSimpleName();
    private List<View> mListViews;

    public WishDetailsPagerAdapter(List<View> mListViews) {
        this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView(mListViews.get(position));//删除页卡
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
        container.addView(mListViews.get(position), 0);//添加页卡
        return mListViews.get(position);
    }

    @Override
    public int getCount() {
        return  mListViews.size();//返回页卡的数量
    }


}

