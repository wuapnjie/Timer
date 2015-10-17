package com.iec.dwx.timer.Activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iec.dwx.timer.Animate.OtherWishDividerItemDecoration;
import com.iec.dwx.timer.R;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
public class OtherWishes extends BaseActivity {
    private final String TAG = OtherWishes.class.getSimpleName();
    private Adapter madapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private RecyclerView mRecyclerView;

    //测试数据
    private String mString[] = new String[]{"aaaa", "bbbbbbbbbbbbbb", "ccccccccccccccccccccc", "d"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.other_wishes_activity_in, R.anim.my_wishes_activity_out);
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "3a39e05d106b31b3f61a8ce842933a8a");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_other_wishes);
        madapter = new Adapter();


        mlayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mlayoutManager);
        mRecyclerView.setAdapter(madapter);
        mRecyclerView.addItemDecoration(new OtherWishDividerItemDecoration(OtherWishes.this));

        ((Toolbar) findViewById(R.id.toolbar_other_wishes)).setNavigationOnClickListener(v -> onBackPressed());
    }

    private class Adapter extends RecyclerView.Adapter<OtherViewHolder> {

        @Override
        public OtherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(OtherWishes.this).inflate(R.layout.other_wishes_item, parent, false);
            return new OtherViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(OtherViewHolder holder, int position) {
            holder.mTime.setText("1995");
            holder.mContent.setText(mString[position]);

        }

        @Override
        public int getItemCount() {
            return mString.length;
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
