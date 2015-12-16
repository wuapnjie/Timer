package com.iec.dwx.timer;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iec.dwx.timer.Activities.BaseActivity;
import com.iec.dwx.timer.Beans.SkillBean;
import com.iec.dwx.timer.Beans.WishBean;
import com.iec.dwx.timer.Utils.DataBaseHelper.DataBaseSkillHelper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2015/11/18 0018.
 */
public class TestActivity extends BaseActivity{
    private final String TAG = TestActivity.class.getSimpleName();
    private int id;

    private Button mBtnSend;
    private TextView mTvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mTvResponse = (TextView) findViewById(R.id.tv_response);

        mBtnSend.setOnClickListener(v -> send());

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);

        Log.d(TAG, "onCreate: deviceID->"+telephonyManager.getDeviceId());
//        SkillBean skillBean1 =new SkillBean();
//        skillBean1.setmCotent("test1");
//        skillBean1.setMarginTop(1);
//        skillBean1.setMarginLeft(1);
//
//        SkillBean skillBean2 =new SkillBean();
//        skillBean2.setmCotent("test2");
//        skillBean2.setMarginTop(2);
//        skillBean2.setMarginLeft(2);
//
//        addBeanTest(skillBean1);
//        addBeanTest(skillBean2);
//
//        getAllBean();
//
//        updateBean(skillBean1, skillBean2);
//
//        getAllBean();
//        deleteBean(id);
//        deleteBean(skillBean1.getmCotent());
    }

    private void send() {
        String path = "http://100.64.132.194:8080/FirstServlet";

        Observable.just(path)
                .map(s -> {
                    WishBean bean = new WishBean();
                    bean.setContent("i am content");
                    bean.setID(9);
                    bean.setPicture("i am path of picture");
                    bean.setTime("i am date");

                    Gson gson = new Gson();
                    String json = gson.toJson(bean);

                    Request request = new Request.Builder().url(path).post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json)).build();
                    try {
                        return new OkHttpClient().newCall(request).execute().body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return e.toString();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mTvResponse.setText(response);
                });

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
