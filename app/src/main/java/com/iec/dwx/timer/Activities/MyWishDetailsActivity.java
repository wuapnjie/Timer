package com.iec.dwx.timer.Activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.iec.dwx.timer.Adapters.WishDetailsPagerAdapter;
import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.Beans.OthersWish;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.DataBaseHelper.DBHelper;
import com.iec.dwx.timer.Utils.Utils;

import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2015/10/16 0016.
 */
public class MyWishDetailsActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    private final String TAG = MyWishDetailsActivity.class.getSimpleName();
    private ViewPager viewPager=null;
    private WishDetailsPagerAdapter madapter=null;
    private List<View> viewList=new ArrayList<View>();
    private int ViewPagerSelectedItem;
    private Boolean EditableFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "3a39e05d106b31b3f61a8ce842933a8a");
        viewPager= (ViewPager) findViewById(R.id.detail_viewPager);


        madapter=new WishDetailsPagerAdapter(getViewList());
        viewPager.setAdapter(madapter);
        choosePager();
        setViewPagerOnPageChangeListener(viewPager);

        ((Toolbar) findViewById(R.id.toolbar_my_wishes_details)).setNavigationOnClickListener(v -> onBackPressed());
        ((Toolbar)findViewById(R.id.toolbar_my_wishes_details)).inflateMenu(R.menu.menu_wishes_details);
        ((Toolbar)findViewById(R.id.toolbar_my_wishes_details)).setOnMenuItemClickListener(this);
    }

    private void setViewPagerOnPageChangeListener(ViewPager viewPager){
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position==0){
                    getSwipeBackLayout().setEdgeSize(Utils.dp2px(200));
                }else {
                    getSwipeBackLayout().setEdgeSize(0);
                }
            }

            @Override
            public void onPageSelected(int position) {
                System.out.println(position+"");
                ViewPagerSelectedItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<View> getViewList(){
        viewList.clear();
        List<CommonBean> data=DBHelper.getInstance(this).getAllBeans(DBHelper.DB_TABLE_WISH);
        View view=null;
        for(int i=0;i<data.size();i++){
            view=LayoutInflater.from(this).inflate(R.layout.detail_textview,null);
            EditText editText= (EditText) view.findViewById(R.id.my_wishes_details_content_editView);
            editText.setText(data.get(i).getContent());
            editText.setEnabled(false);
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
            ViewPagerSelectedItem=flag;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_my_wishes_detail_share:
                System.out.println("menu_my_wishes_detail_share");
                 clickedShare();
                break;
            case R.id.menu_my_wishes_detail_edit:
                System.out.println("menu_my_wishes_detail_delete");
                clickedEdit();
                break;
        }
        return false;
    }

    //分享键被点击
    private void clickedShare(){
        List<CommonBean> data=DBHelper.getInstance(this).getAllBeans(DBHelper.DB_TABLE_WISH);
//        if(commonBean.getPicture().equals("0")){
//            commonBean.save(this, new SaveListener() {
//               @Override
//               public void onSuccess() {
//                   commonBean.setPicture("1");
//                   DBHelper.getInstance(MyWishDetailsActivity.this).updateBean(DBHelper.DB_TABLE_WISH, commonBean.getID(), commonBean);
//                   Toast.makeText(MyWishDetailsActivity.this,"成功分享",Toast.LENGTH_SHORT).show();
//               }
//
//               @Override
//               public void onFailure(int i, String s) {
//                   Toast.makeText(MyWishDetailsActivity.this,"分享失败",Toast.LENGTH_SHORT).show();
//               }
//           });
//        }else{
//            Toast.makeText(MyWishDetailsActivity.this,"已经分享过了",Toast.LENGTH_SHORT).show();
//        }
        OthersWish othersWish = new OthersWish(data.get(ViewPagerSelectedItem).getContent(),0,0);
        othersWish.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MyWishDetailsActivity.this,"成功分享",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MyWishDetailsActivity.this,"分享失败",Toast.LENGTH_SHORT).show();
            }
        });


    }

    //删除键被点击,现在被改为修改了
    private void clickedEdit(){
        List<CommonBean> data=DBHelper.getInstance(this).getAllBeans(DBHelper.DB_TABLE_WISH);
        CommonBean commonBean=data.get(ViewPagerSelectedItem);
        EditText editText= (EditText) viewList.get(ViewPagerSelectedItem).findViewById(R.id.my_wishes_details_content_editView);

        if(!EditableFlag){
            editText.setEnabled(true);
            ((ActionMenuItemView)findViewById(R.id.menu_my_wishes_detail_edit)).setIcon(getResources().getDrawable(R.drawable.ic_done_black_24dp));
            EditableFlag=!EditableFlag;
        }else {
            commonBean.setContent(editText.getText().toString());
            commonBean.setPicture(0+"");
            DBHelper.getInstance(this).updateBean(DBHelper.DB_TABLE_WISH, commonBean.getID(), commonBean);
            editText.setEnabled(false);

            EditableFlag=!EditableFlag;
            ((ActionMenuItemView)findViewById(R.id.menu_my_wishes_detail_edit)).setIcon(getResources().getDrawable(R.drawable.ic_mode_edit_black_24dp));
            Toast.makeText(this,"修改成功",Toast.LENGTH_LONG).show();
        }
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
