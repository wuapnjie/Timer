package com.iec.dwx.timer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.iec.dwx.timer.Activities.BaseActivity;
import com.iec.dwx.timer.Activities.MainActivity;
import com.iec.dwx.timer.Activities.TimeActivity;
import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.Beans.WishBean;
import com.iec.dwx.timer.Content.PhotoAlbumManager;
import com.iec.dwx.timer.Utils.DBHelper;
import com.iec.dwx.timer.Utils.ScreenSizeUtils;

import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class TestActivity extends BaseActivity {


    @Bind(R.id.btn_test_1)
    Button mBtnTest1;
    @Bind(R.id.btn_test_2)
    Button mBtnTest2;
    @Bind(R.id.btn_test_3)
    Button mBtnTest3;
    @Bind(R.id.btn_test_4)
    Button mBtnTest4;
    @Bind(R.id.btn_test_5)
    Button mBtnTest5;
    @Bind(R.id.btn_test_6)
    Button mBtnTest6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bmob.initialize(this, "3a39e05d106b31b3f61a8ce842933a8a");
        mSwipeBackLayout.setEnableGesture(false);

        WishBean bean = new WishBean();
        bean.setContent("我想长大");
        bean.setTime("2015-10-10");
        bean.setPicture("null");

        WishBean bean2 = new WishBean();
        bean2.setContent("我要吃屎");
        bean2.setTime("2015-10-11");
        bean2.setPicture("hello.jpg");

        mBtnTest1.setOnClickListener(v -> addBean(DBHelper.DB_TABLE_ACHIEVEMENT, bean));
        mBtnTest2.setOnClickListener(v -> deleteBean(DBHelper.DB_TABLE_ACHIEVEMENT, bean));
        mBtnTest3.setOnClickListener(v -> getAllBeans(DBHelper.DB_TABLE_ACHIEVEMENT));
        mBtnTest4.setOnClickListener(v -> getOneBean(DBHelper.DB_TABLE_ACHIEVEMENT, 2));
        mBtnTest5.setOnClickListener(v -> updateBean(DBHelper.DB_TABLE_ACHIEVEMENT, bean, bean2));
        mBtnTest6.setOnClickListener(v -> linkTo());


        System.out.println((int)(Math.random() * ScreenSizeUtils.getHeight(this)) + "");
//        System.out.println(Math.random()+"");
//        System.out.println(Math.random()+"");
//        System.out.println(Math.random()+"");
//        System.out.println(Math.random()+"");
//        System.out.println(Math.random()+"");



    }

    private void test() {
        PhotoAlbumManager manager = PhotoAlbumManager.initial(this);
        manager.getBucket();
        manager.test();
        manager.getThumbnail();
    }

    private void updateBean(String tableName, CommonBean oldBean, CommonBean newBean) {
        DBHelper.getInstance(this).updateBean(tableName, oldBean, newBean);
    }

    private void getOneBean(String tableName, int id) {
        System.out.println("--------------getOneBean-------------");
        CommonBean bean = DBHelper.getInstance(this).getOneBean(tableName, id);
        System.out.println(":ID->" + bean.getID() + ",Time->" + bean.getTime() + ",Content->" + bean.getContent() + ",Picture->" + bean.getPicture());
    }

    private void getAllBeans(String tableName) {
        List<CommonBean> list = DBHelper.getInstance(this).getAllBeans(tableName);
        System.out.println("---------------getAllBeans---------------");
        for (CommonBean commonBean : list) {
            System.out.println(":ID->" + commonBean.getID() + ",Time->" + commonBean.getTime() + ",Content->" + commonBean.getContent() + ",Picture->" + commonBean.getPicture());
        }
    }

    private void deleteBean(String tableName, WishBean bean) {
        DBHelper.getInstance(this).deleteBean(tableName, bean);
    }

    private void addBean(String tableName, WishBean bean) {
        DBHelper.getInstance(this).addBeanToDatabase(tableName, bean);
    }

    private void linkTo() {
        startActivity(new Intent(this, TimeActivity.class));
//        startActivity(new Intent(this,MainActivity.class));
    }

    private List getBmobData(){
        List<CommonBean> list=null;
        BmobQuery<CommonBean> query=new BmobQuery<CommonBean>();
        query.setLimit(20);
        query.findObjects(this, new FindListener<CommonBean>() {
            @Override
            public void onSuccess(List<CommonBean> list) {
                System.out.println("查询成功：共" + list.size() + "条数据。");
            }

            @Override
            public void onError(int i, String s) {
                System.out.println("查询失败：" + s);
            }
        });
        return list;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_test;
    }

    @Override
    protected int getEdgeSize() {
        return 0;
    }


}
