package com.iec.dwx.timer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.iec.dwx.timer.Adapters.WishDetailsPagerAdapter;
import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.Beans.WishBean;
import com.iec.dwx.timer.Fragments.MyWishesFragment;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2015/10/16 0016.
 */
public class MyWishDetails extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    private final String TAG = MyWishDetails.class.getSimpleName();
    private ViewPager viewPager=null;
    private WishDetailsPagerAdapter madapter=null;
    private List<View> viewList=new ArrayList<View>();
    private int ViewPagerSelectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "3a39e05d106b31b3f61a8ce842933a8a");
        viewPager= (ViewPager) findViewById(R.id.detail_viewPager);
        madapter=new WishDetailsPagerAdapter(getViewList());
        viewPager.setAdapter(madapter);
        choosePager();
        setViewPagerOnPageChangeListener(viewPager);
    }

    private void setViewPagerOnPageChangeListener(ViewPager viewPager){
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ViewPagerSelectedItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<View> getViewList(){
        List<CommonBean> data=DBHelper.getInstance(this).getAllBeans(DBHelper.DB_TABLE_WISH);
        View view=null;
        for(int i=0;i<data.size();i++){
            view=LayoutInflater.from(this).inflate(R.layout.detail_textview,null);
            ((TextView)view.findViewById(R.id.my_wishes_details_content_textview)).setText(
                    data.get(i).getContent());
            ((TextView)view.findViewById(R.id.my_wishes_details_time_textview)).setText(
                    data.get(i).getTime()
            );
            viewList.add(view);
        }

        return viewList;
    }

    private void choosePager(){
        int flag;
        if(( flag=getIntent().getIntExtra("ClickPosition",-1))>-1){
            viewPager.setCurrentItem(flag);
        }
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
                System.out.println("menu_my_wishes_detail_share");
                 clickedShare();
                break;
            case R.id.menu_my_wishes_detail_delete:
                System.out.println("menu_my_wishes_detail_delete");
                clickedDelete();
                break;
        }
        return false;
    }

    //分享键被点击
    private void clickedShare(){
        List<CommonBean> data=DBHelper.getInstance(this).getAllBeans(DBHelper.DB_TABLE_WISH);
        CommonBean commonBean=data.get(ViewPagerSelectedItem);
        if(commonBean.getPicture().equals("0")){
            commonBean.save(this, new SaveListener() {
               @Override
               public void onSuccess() {
                   commonBean.setPicture("1");
                   DBHelper.getInstance(MyWishDetails.this).updateBean(DBHelper.DB_TABLE_WISH, commonBean.getID(), commonBean);
                   Toast.makeText(MyWishDetails.this,"成功分享",Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onFailure(int i, String s) {
                   Toast.makeText(MyWishDetails.this,"分享失败",Toast.LENGTH_SHORT).show();
               }
           });
        }else{
            Toast.makeText(MyWishDetails.this,"已经分享过了",Toast.LENGTH_SHORT).show();
        }

    }

    //删除键被点击
    private void clickedDelete(){
        List<CommonBean> data=DBHelper.getInstance(this).getAllBeans(DBHelper.DB_TABLE_WISH);
        CommonBean commonBean=data.get(ViewPagerSelectedItem);
        System.out.println(ViewPagerSelectedItem+"");
        DBHelper.getInstance(this).deleteBean(DBHelper.DB_TABLE_WISH, commonBean);
        viewPager.removeView(viewList.get(ViewPagerSelectedItem));

        viewPager.setAdapter(new WishDetailsPagerAdapter(getViewList()));
        Toast.makeText(this,"已删除",Toast.LENGTH_LONG).show();
    }

    @Override
    protected int getEdgeSize() {
        return 0;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_my_wishes_detail;
    }

}
