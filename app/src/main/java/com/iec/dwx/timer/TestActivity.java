package com.iec.dwx.timer;

import android.os.Bundle;
import android.util.Log;

import com.iec.dwx.timer.Activities.BaseActivity;
import com.iec.dwx.timer.Beans.SkillBean;
import com.iec.dwx.timer.Beans.SkillBeanNew;
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
        SkillBeanNew skillBeanNew1=new SkillBeanNew();
        skillBeanNew1.setmCotent("test1");
        skillBeanNew1.setMarginTop(1);
        skillBeanNew1.setMarginLeft(1);

        SkillBeanNew skillBeanNew2=new SkillBeanNew();
        skillBeanNew2.setmCotent("test2");
        skillBeanNew2.setMarginTop(2);
        skillBeanNew2.setMarginLeft(2);

        addBeanTest(skillBeanNew1);
        addBeanTest(skillBeanNew2);

        getAllBean();

        deleteBean(id);
        deleteBean(skillBeanNew1.getmCotent());
    }

    private void addBeanTest(SkillBeanNew skillBeanNew){
        id=DataBaseSkillHelper.getInstance(this).addBean(skillBeanNew);
        Log.e(TAG, ""+id);
    }

    private void deleteBean(int id){
        Log.e(TAG,""+DataBaseSkillHelper.getInstance(this).deleteOneBean(id));
    }

    private void deleteBean(String content){
        Log.e(TAG,""+DataBaseSkillHelper.getInstance(this).deleteOneBean(content));
    }

    private void getAllBean(){
        List<SkillBeanNew> list=DataBaseSkillHelper.getInstance(this).getAllBeans();
        for(SkillBeanNew data:list){
            Log.e(TAG,data.toString());
        }
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
