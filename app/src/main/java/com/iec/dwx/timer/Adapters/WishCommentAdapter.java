package com.iec.dwx.timer.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iec.dwx.timer.Beans.WishComment;
import com.iec.dwx.timer.R;

import java.util.List;

/**
 * Created by Flying SnowBean on 2015/12/13.
 */
public class WishCommentAdapter extends RecyclerView.Adapter<WishCommentAdapter.WishCommentViewHolder> {
    private final String TAG = WishCommentAdapter.class.getSimpleName();
    private Context mContext;
    private List<WishComment> mData;

    public WishCommentAdapter(Context context) {
        mContext = context;
    }

    public WishCommentAdapter(Context context, List<WishComment> data) {
        mContext = context;
        mData = data;
    }

    public void setData(List<WishComment> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public List<WishComment> getData() {
        return mData;
    }

    @Override
    public WishCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_wish_comment, parent, false);
        return new WishCommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WishCommentViewHolder holder, int position) {
        WishComment wishComment = mData.get(position);
        holder.mTvComment.setText(wishComment.getContent());
        holder.mTvTime.setText(wishComment.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class WishCommentViewHolder extends RecyclerView.ViewHolder {
        TextView mTvComment;
        TextView mTvTime;

        public WishCommentViewHolder(View itemView) {
            super(itemView);
            mTvComment = (TextView) itemView.findViewById(R.id.tv_comment);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_comment_time);
        }
    }
}
