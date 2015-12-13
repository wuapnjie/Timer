package com.iec.dwx.timer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iec.dwx.timer.Beans.OthersWish;
import com.iec.dwx.timer.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
public class OtherWishesActivity extends BaseActivity {
    private final String TAG = OtherWishesActivity.class.getSimpleName();
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.other_wishes_activity_in, R.anim.my_wishes_activity_out);
        super.onCreate(savedInstanceState);

        initFindView();
        initSwipeRefreshLayout();

        getBmobData();

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ((Toolbar) findViewById(R.id.toolbar_other_wishes)).setNavigationOnClickListener(v -> onBackPressed());
    }

    /**
     * 初始化findviewbyid还有bmob初始化
     */
    private void initFindView() {
        Bmob.initialize(this, "3a39e05d106b31b3f61a8ce842933a8a");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_other_wishes);
    }

    /**
     * 初始化刷新组建
     */
    private void initSwipeRefreshLayout() {
        SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.other_wishes_swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //do your fucking coding refresh here!!
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });
    }

    private void getBmobData() {
        BmobQuery<OthersWish> query = new BmobQuery<>();
        query.setLimit(20);
        query.findObjects(this, new FindListener<OthersWish>() {
            @Override
            public void onSuccess(List<OthersWish> list) {
                System.out.println("查询成功：共" + list.size() + "条数据。");
                for (OthersWish wish : list) {
                    System.out.println(wish.getContent());
                }
                Adapter adapter = new Adapter(list);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(OtherWishesActivity.this, "网络异常,请检测是否联网，下拉刷新", Toast.LENGTH_LONG).show();
                System.out.println("查询失败：" + s);
            }
        });
    }

    private class Adapter extends RecyclerView.Adapter<OtherViewHolder> {

        private List<OthersWish> mData = null;

        public Adapter(List data) {
            super();
            mData = data;
        }

        @Override
        public OtherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(OtherWishesActivity.this).inflate(R.layout.other_wishes_item, parent, false);
            return new OtherViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(OtherViewHolder holder, int position) {
            OthersWish wish = mData.get(position);
            holder.mContent.setOnClickListener(v -> {
                int[] location = new int[2];
                holder.mContent.getLocationOnScreen(location);
                Bundle bundle = new Bundle();
                bundle.putSerializable("wish",wish);
                bundle.putIntArray("location",location);
                Intent intent = new Intent(OtherWishesActivity.this, WishCommentActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(0,0);
            });

            holder.mTime.setText(wish.getCreatedAt());
            holder.mContent.setText(wish.getContent());
            holder.mTvLikeNum.setText(wish.getLikeNumber() + " likes");

            holder.mIvLike.setOnClickListener(v -> {
                wish.setLikeNumber(wish.getLikeNumber() + 1);
                wish.update(OtherWishesActivity.this, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        holder.mTvLikeNum.setText(wish.getLikeNumber() + " likes");
                        holder.mIvLike.setBackgroundResource(R.drawable.ic_favorite_red_400_24dp);
                        Toast.makeText(OtherWishesActivity.this, "You liked this wish", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(OtherWishesActivity.this, "Failured,please retry", Toast.LENGTH_SHORT).show();
                    }
                });
            });

            holder.mIvComment.setOnClickListener(v -> {
                int[] location = new int[2];
                holder.mIvComment.getLocationOnScreen(location);
                Bundle bundle = new Bundle();
                bundle.putSerializable("wish",wish);
                bundle.putIntArray("location",location);
                Intent intent = new Intent(OtherWishesActivity.this, WishCommentActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(0,0);
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    private class OtherViewHolder extends RecyclerView.ViewHolder {
        TextView mContent;
        TextView mTime;
        ImageView mIvLike;
        ImageView mIvComment;
        TextView mTvLikeNum;

        public OtherViewHolder(View itemView) {
            super(itemView);
            mContent = (TextView) itemView.findViewById(R.id.other_wishes_text);
            mTime = (TextView) itemView.findViewById(R.id.other_wishes_time);
            mIvComment = (ImageView) itemView.findViewById(R.id.iv_comments);
            mIvLike = (ImageView) itemView.findViewById(R.id.iv_like);
            mTvLikeNum = (TextView) itemView.findViewById(R.id.tv_like_num);
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
