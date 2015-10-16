package com.iec.dwx.timer.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iec.dwx.timer.Beans.AlbumBean;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.CacheManager.SMemoryCacheManager;
import com.iec.dwx.timer.Utils.ImageUtils;
import com.iec.dwx.timer.Utils.Utils;
import com.iec.dwx.timer.Views.SquareImageView;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Flying SnowBean on 2015/10/16.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private final String TAG = AlbumAdapter.class.getSimpleName();
    private List<AlbumBean> data;
    private OnAlbumClickListener mOnAlbumClickListener;
    private Context mCtx;

    public AlbumAdapter(Context ctx) {
        mCtx = ctx;
    }

    public void obtainData(List<AlbumBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnAlbumClickListener(OnAlbumClickListener onAlbumClickListener) {
        mOnAlbumClickListener = onAlbumClickListener;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mCtx).inflate(R.layout.album_item, parent, false);
        return new AlbumViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        holder.mTextView.setText(data.get(position).getBucketName());
        Observable.just(data.get(position).getThumbnailPath())
                .map(s -> {
                    if (SMemoryCacheManager.getInstance().getBitmap(s) != null)
                        return SMemoryCacheManager.getInstance().getBitmap(s);
                    else
                        return ImageUtils.decodeFromFile(s, Utils.dp2px(100), Utils.dp2px(100));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    holder.mImageView.setImageBitmap(bitmap);
                    SMemoryCacheManager.getInstance().putBitmap(data.get(position).getThumbnailPath(), bitmap);
                });
        holder.mContainer.setOnClickListener(v -> {
            if (mOnAlbumClickListener != null)
                mOnAlbumClickListener.onAlbumClick(v, position, data.get(position).getBucketID());
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        SquareImageView mImageView;
        TextView mTextView;
        LinearLayout mContainer;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            mImageView = (SquareImageView) itemView.findViewById(R.id.iv_album_thumbnail);
            mTextView = (TextView) itemView.findViewById(R.id.tv_album_bucket_name);
            mContainer = (LinearLayout) itemView.findViewById(R.id.album_container);
        }
    }

    public interface OnAlbumClickListener {
        void onAlbumClick(View v, int position, String args);
    }
}
