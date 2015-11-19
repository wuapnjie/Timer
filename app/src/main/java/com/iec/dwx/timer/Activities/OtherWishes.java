package com.iec.dwx.timer.Activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import android.os.Handler;
/**
 * Created by Administrator on 2015/10/5 0005.
 */
public class OtherWishes extends BaseActivity {
    private final String TAG = OtherWishes.class.getSimpleName();
    private RecyclerView.LayoutManager mlayoutManager;
    private RecyclerView mRecyclerView;
    private Adapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.other_wishes_activity_in, R.anim.my_wishes_activity_out);
        super.onCreate(savedInstanceState);

        initFindView();
        initSwipeRefreshLayout();

        getBmobData();

        mlayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mlayoutManager);

        ((Toolbar) findViewById(R.id.toolbar_other_wishes)).setNavigationOnClickListener(v -> onBackPressed());
    }

    /**
     * 初始化findviewbyid还有bmob初始化
     */
    private void initFindView(){
        Bmob.initialize(this, "3a39e05d106b31b3f61a8ce842933a8a");
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_other_wishes);
    }

    /**
     * 初始化刷新组建
     */
    private void initSwipeRefreshLayout(){
        SwipeRefreshLayout mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.other_wishes_swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFromBmobData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * 第一次获取bmob的数据
     */
    private void getBmobData(){
        BmobQuery<CommonBean> query=new BmobQuery<CommonBean>();
        query.order("-mTime");
        query.setLimit(20);
        query.findObjects(this, new FindListener<CommonBean>() {
            @Override
            public void onSuccess(List<CommonBean> list) {
                System.out.println("查询成功：共" + list.size() + "条数据。");
                List<CommonBean> adapterData = list;
                for (CommonBean commonBean : adapterData) {
                    System.out.println(commonBean.getContent());
                }
                madapter = new Adapter(list);
                mRecyclerView.setAdapter(madapter);

            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(OtherWishes.this, "网络异常,请检测是否联网，下拉刷新", Toast.LENGTH_LONG).show();
                System.out.println("查询失败：" + s);
            }
        });
    }

    private void refreshFromBmobData(){
        BmobQuery<CommonBean> query=new BmobQuery<CommonBean>();
        query.order("-mTime");
        query.setLimit(20);
        query.findObjects(this, new FindListener<CommonBean>() {
            @Override
            public void onSuccess(List<CommonBean> list) {
                System.out.println("查询成功：共" + list.size() + "条数据。");
                madapter.refreshData(list);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(OtherWishes.this,"网络异常,请检测是否联网，下拉刷新",Toast.LENGTH_LONG).show();
                System.out.println("查询失败："+s);
            }
        });
    }

    private class Adapter extends RecyclerView.Adapter<OtherViewHolder> {

        private List<CommonBean> mData=null;
        public Adapter(List Data){
            super();
            mData=Data;
        }

        public void refreshData(List data){
            mData=data;
            notifyDataSetChanged();
        }

        @Override
        public OtherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(OtherWishes.this).inflate(R.layout.other_wishes_item, parent, false);
            return new OtherViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(OtherViewHolder holder, int position) {
            holder.mTime.setText(mData.get(position).getTime());
            holder.mContent.setText(mData.get(position).getContent());
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    private class OtherViewHolder extends RecyclerView.ViewHolder {
        TextView mContent;
        TextView mTime;
        public OtherViewHolder(View itemView) {
            super(itemView);
            mContent = (TextView) itemView.findViewById(R.id.other_wishes_text);
            mTime = (TextView) itemView.findViewById(R.id.other_wishes_time);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.my_wishes_activity_in, R.anim.other_wishes_activity_out);
    }

    @Override
    protected int getEdgeSize() {
        return 0;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_other_wishes;
    }


}
