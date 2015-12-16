package com.iec.dwx.timer.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iec.dwx.timer.Beans.Notification;
import com.iec.dwx.timer.R;

import java.util.List;

/**
 * Created by Flying SnowBean on 2015/12/16.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private final String TAG = NotificationAdapter.class.getSimpleName();
    private List<Notification> mData;
    private Context mCtx;
    private OnFeedClickListener mOnFeedClickListener;

    public NotificationAdapter(Context ctx) {
        mCtx = ctx;
    }

    public void setOnFeedClickListener(OnFeedClickListener onFeedClickListener) {
        mOnFeedClickListener = onFeedClickListener;
    }

    public List<Notification> getData() {
        return mData;
    }

    public void setData(List<Notification> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == 0) {
            itemView = LayoutInflater.from(mCtx).inflate(R.layout.item_notification_like, parent, false);
        } else {
            itemView = LayoutInflater.from(mCtx).inflate(R.layout.item_notification_comment, parent, false);
        }

        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = mData.get(position);

        holder.mTvWishContent.setText(notification.getWish().getContent());
        holder.mTvWishTime.setText(notification.getWish().getCreatedAt());

        holder.mContainer.setOnClickListener(v -> {
            if (mOnFeedClickListener != null) {
                mOnFeedClickListener.onFeedClicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData == null)
            return super.getItemViewType(position);
        return mData.get(position).getType();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mContainer;
        TextView mTvWishContent;
        TextView mTvWishTime;

        public NotificationViewHolder(View itemView) {
            super(itemView);

            mContainer = (LinearLayout) itemView.findViewById(R.id.notification_container);
            mTvWishContent = (TextView) itemView.findViewById(R.id.my_wishes_text);
            mTvWishTime = (TextView) itemView.findViewById(R.id.my_wishes_time);
        }
    }

    public interface OnFeedClickListener {
        void onFeedClicked(View view, int position);
    }

}
