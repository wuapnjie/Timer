package com.iec.dwx.timer.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iec.dwx.timer.R;

public class MyWishesFragment extends Fragment {

    private MyViewHolder myViewHolder=null;
    private float clickDownY;
    private float clickUpY;

    //设定最小滑动距离
    private  float Y_DISTANCE_SLIDE=30f;


    public static MyWishesFragment newInstance() {
        return new MyWishesFragment();
    }

    public MyWishesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myViewHolder=new MyViewHolder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_wishes, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化myViewHolder
        if(myViewHolder.getFlag()){

            myViewHolder=new MyViewHolder();
            myViewHolder.setmTv((TextView) getActivity().findViewById(R.id.fragment_my_wishes_tv));
            myViewHolder.setmImg((ImageView) getActivity().findViewById(R.id.fragment_my_wishes_iv));

            myViewHolder.changeFlag(false);
        }

        //监听点击事件,按道理说不需要，但是不写这个划动也会失效
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("fragment_my_wishes", "该onclik不需要使用");
            }
        });

        //监听划动与点击事件
        getView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //利用落点和起点之间的Y距离来判断竖划
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        clickDownY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        clickUpY = event.getY();
                        MovingMethod(clickUpY - clickDownY);
                        onClickMethod(clickUpY - clickDownY);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });



    }




    //点击响应事件
    private void onClickMethod(float y){
        if(y==0f){
            Log.e("fragment_my_wishes", "真正点击应处理的事件");
        }

    }

    //划动响应事件
    private  void MovingMethod(float y){
        if(y<-Y_DISTANCE_SLIDE){
            myViewHolder.getmImg().setImageResource(R.drawable.b);
            Log.e("fragment_my_wishes", y + "上划");
        }else if(y>Y_DISTANCE_SLIDE){
            myViewHolder.getmImg().setImageResource(R.drawable.b);
            Log.e("fragment_my_wishes", y + "下划");
        }
    }


    //上划响应事件
    private void upMovingMehtod(){
        //数据尚未拥有

    }

    //下划响应事件
    private void downMovingMethod(){
       //数据尚未拥有
    }


    //Id管理类
    private class MyViewHolder{
        private ImageView mImg=null;
        private TextView mTv=null;
        private Boolean flag=null;

        public MyViewHolder(){
            flag=true;
        }
        public Boolean getFlag(){
            return flag;
        }

        public void changeFlag(Boolean flag){
            flag=this.flag;
        }

        public ImageView getmImg() {
            return mImg;
        }

        public TextView getmTv() {
            return mTv;
        }

        public void setmImg(ImageView mImg) {
            this.mImg = mImg;
        }

        public void setmTv(TextView mTv) {
            this.mTv = mTv;
        }
    }

}
