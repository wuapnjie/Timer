package com.iec.dwx.timer.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.CacheManager.SMemoryCacheManager;
import com.iec.dwx.timer.Utils.ImageUtils;
import com.iec.dwx.timer.Utils.ScreenSizeUtils;
import com.iec.dwx.timer.Utils.Utils;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Flying SnowBean on 2015/10/18.
 */
public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {
    private List<CommonBean> mData;
    private OnAchievementLongClickListener mOnAchievementLongClickListener;
    private OnAchievementClickListener mOnAchievementClickListener;
    private boolean mIsItemSelected = false;
    private Context mCtx;

    public AchievementAdapter(Context ctx) {
        mCtx = ctx;
    }

    public List<CommonBean> getData() {
        return mData;
    }

    public void obtainData(List<CommonBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public boolean isItemSeleted() {
        return mIsItemSelected;
    }

    public void setIsItemSelected(boolean isItemSelected) {
        mIsItemSelected = isItemSelected;
    }

    public void setOnAchievementLongClickListener(OnAchievementLongClickListener onAchievementLongClickListener) {
        mOnAchievementLongClickListener = onAchievementLongClickListener;
    }

    public void setOnAchievementClickListener(OnAchievementClickListener onAchievementClickListener) {
        mOnAchievementClickListener = onAchievementClickListener;
    }

    @Override
    public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mCtx).inflate(R.layout.achivement_item, parent, false);
        return new AchievementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AchievementViewHolder holder, int position) {
        holder.mImageView.setLayoutParams(new LinearLayout.LayoutParams(ScreenSizeUtils.getWidth(mCtx), ScreenSizeUtils.getWidth(mCtx)));
        //设置文字
        holder.mTextContent.setText(mData.get(position).getContent());
        holder.mTextTime.setText(mData.get(position).getTime());

        //设置长按事件
        holder.mContainer.setOnLongClickListener(v -> {
            if (mOnAchievementLongClickListener != null)
                mOnAchievementLongClickListener.onAchievementLongClick(v, position);
            holder.mShader.setVisibility(View.VISIBLE);
            return true;
        });

        //设置单击事件
        holder.mContainer.setOnClickListener(v -> {
            if (holder.mShader.getVisibility() == View.VISIBLE) {
                holder.mShader.setVisibility(View.GONE);
            }
            if (mOnAchievementClickListener != null)
                mOnAchievementClickListener.onAchievementClick(v, holder.getLayoutPosition());
        });
        //设置图画
        Observable.just(mData.get(position).getPicture())
                .map(s -> {
                    if (SMemoryCacheManager.getInstance().getBitmap(s) != null)
                        return SMemoryCacheManager.getInstance().getBitmap(s);
                    else {
                        Bitmap bitmap = ImageUtils.decodeFromFile(s, Utils.dp2px(200), Utils.dp2px(200));
                        SMemoryCacheManager.getInstance().putBitmap(mData.get(position).getPicture(), bitmap);
                        return bitmap;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> holder.mImageView.setImageBitmap(bitmap));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void deleteItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }


    public static class AchievementViewHolder extends RecyclerView.ViewHolder {
        TextView mTextTime;
        TextView mTextContent;
        ImageView mImageView;
        CardView mContainer;
        View mShader;

        public AchievementViewHolder(View itemView) {
            super(itemView);
            mTextTime = (TextView) itemView.findViewById(R.id.tv_achievement_time);
            mTextContent = (TextView) itemView.findViewById(R.id.tv_achievement_head);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_achievement_photo);
            mContainer = (CardView) itemView.findViewById(R.id.achievement_container);
            mShader = itemView.findViewById(R.id.achievement_shader);
        }
    }

    public interface OnAchievementLongClickListener {
        void onAchievementLongClick(View v, int position);
    }

    public interface OnAchievementClickListener {
        void onAchievementClick(View v, int position);
    }
}
