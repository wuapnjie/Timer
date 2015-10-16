package com.iec.dwx.timer.Activities;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.iec.dwx.timer.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/16 0016.
 */
public class MyWishDetails extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    private final String TAG = MyWishDetails.class.getSimpleName();
    private ViewPager viewPager=null;
    private MyViewPagerAdapter madapter=null;
    private List<View> viewList=new ArrayList<View>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager= (ViewPager) findViewById(R.id.detail_viewPager);
        View view1=LayoutInflater.from(this).inflate(R.layout.detail_textview,null);
        View view2=LayoutInflater.from(this).inflate(R.layout.detail_textview,null);
        viewList.add(view1);
        viewList.add(view2);
        madapter=new MyViewPagerAdapter(viewList);
        viewPager.setAdapter(madapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((Toolbar)findViewById(R.id.toolbar_my_wishes_details)).inflateMenu(R.menu.menu_wishes_details);
        ((Toolbar)findViewById(R.id.toolbar_my_wishes_details)).setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_my_wishes_detail_share:
                Toast.makeText(this,"Shared",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_my_wishes_detail_delete:
                Toast.makeText(this,"Deleted",Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }

    @Override
    protected int getEdgeSize() {
        return 0;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_my_wishes_detail;
    }



    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)   {
            container.removeView(mListViews.get(position));//删除页卡
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

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;//官方提示这样写
        }
    }
}
