package com.iec.dwx.timer.Activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.R;
import java.util.List;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
public class OtherWishes extends BaseActivity {
    private final String TAG = OtherWishes.class.getSimpleName();
    private Adapter madapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private RecyclerView mRecyclerView;
    private List<CommonBean> adapterData=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.other_wishes_activity_in, R.anim.my_wishes_activity_out);
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "3a39e05d106b31b3f61a8ce842933a8a");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_other_wishes);
        getBmobData();
        mlayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mlayoutManager);

        ((Toolbar) findViewById(R.id.toolbar_other_wishes)).setNavigationOnClickListener(v -> onBackPressed());
    }


    private void getBmobData(){
        BmobQuery<CommonBean> query=new BmobQuery<CommonBean>();
        query.setLimit(20);
        query.findObjects(this, new FindListener<CommonBean>() {
            @Override
            public void onSuccess(List<CommonBean> list) {
                System.out.println("查询成功：共" + list.size() + "条数据。");
                adapterData=list;
                for(CommonBean commonBean:adapterData){
                    System.out.println(commonBean.getContent());
                }
                madapter=new Adapter(list);
                mRecyclerView.setAdapter(madapter);

            }

            @Override
            public void onError(int i, String s) {
                adapterData=null;
                System.out.println("查询失败："+s);
            }
        });
    }

    private class Adapter extends RecyclerView.Adapter<OtherViewHolder> {

        private List<CommonBean> mData=null;
        public Adapter(List Data){
            super();
            mData=Data;
            for(CommonBean commonBean:mData){
                Log.e("eric",commonBean.getContent());
            }
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
