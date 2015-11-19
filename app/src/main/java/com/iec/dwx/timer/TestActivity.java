package com.iec.dwx.timer;

import android.os.Bundle;
import android.util.Log;

import com.iec.dwx.timer.Activities.BaseActivity;
import com.iec.dwx.timer.Beans.SkillBean;
import com.iec.dwx.timer.Utils.DataBaseHelper.DataBaseSkillHelper;

import java.util.List;

/**
 * Created by Administrator on 2015/11/18 0018.
 */
public class TestActivity extends BaseActivity{
    private final String TAG = TestActivity.class.getSimpleName();
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkillBean skillBean1 =new SkillBean();
        skillBean1.setmCotent("test1");
        skillBean1.setMarginTop(1);
        skillBean1.setMarginLeft(1);

        SkillBean skillBean2 =new SkillBean();
        skillBean2.setmCotent("test2");
        skillBean2.setMarginTop(2);
        skillBean2.setMarginLeft(2);

        addBeanTest(skillBean1);
        addBeanTest(skillBean2);

        getAllBean();

        updateBean(skillBean1, skillBean2);

        getAllBean();
//        deleteBean(id);
//        deleteBean(skillBean1.getmCotent());
    }

    private void addBeanTest(SkillBean skillBean){
        id=DataBaseSkillHelper.getInstance(this).addBean(skillBean);
        Log.e(TAG, ""+id);
    }

    private void deleteBean(int id){
        Log.e(TAG,""+DataBaseSkillHelper.getInstance(this).deleteOneBean(id));
    }

    private void deleteBean(String content){
        Log.e(TAG,""+DataBaseSkillHelper.getInstance(this).deleteOneBean(content));
    }

    private void getAllBean(){
        List<SkillBean> list=DataBaseSkillHelper.getInstance(this).getAllBeans();
        for(SkillBean data:list){
            Log.e(TAG,data.getmId()+"");
            Log.e(TAG,data.getmCotent());
            Log.e(TAG,data.getMarginLeft()+"");
            Log.e(TAG,data.getMarginTop()+"");
        }
    }

    private void updateBean(int id){
        SkillBean skillBean3 =new SkillBean();
        skillBean3.setmCotent("test3");
        skillBean3.setMarginTop(3);
        skillBean3.setMarginLeft(3);
        Log.e(TAG, DataBaseSkillHelper.getInstance(this).updateOneBean(id, skillBean3)+"");
    }

    private void updateBean(SkillBean oldBean,SkillBean newBean){
        Log.e(TAG,DataBaseSkillHelper.getInstance(this).updateOneBean(oldBean,newBean)+"");
    }

    @Override
    protected int getEdgeSize() {
        return 0;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.test_activity_layout;
    }
}
