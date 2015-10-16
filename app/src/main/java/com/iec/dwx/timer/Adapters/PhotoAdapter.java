package com.iec.dwx.timer.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

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
 * Created by Flying SnowBean on 2015/10/16.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private final String TAG = PhotoAdapter.class.getSimpleName();
    private Context mCtx;
    private List<String> data;
    private OnPhotoClickListener mOnPhotoClickListener;

    public PhotoAdapter(Context ctx) {
        mCtx = ctx;
    }

    public void obtainData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        mOnPhotoClickListener = onPhotoClickListener;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mCtx).inflate(R.layout.photo_item, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.mContainer.setLayoutParams(new ViewGroup.LayoutParams(ScreenSizeUtils.getWidth(mCtx) / 3, ScreenSizeUtils.getWidth(mCtx) / 3));
        holder.mContainer.setOnClickListener(v -> {
            if (mOnPhotoClickListener != null)
                mOnPhotoClickListener.onPhotoClick(v, position, data.get(position));
        });
        Observable.just(data.get(position))
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
                    SMemoryCacheManager.getInstance().putBitmap(data.get(position), bitmap);
                });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        FrameLayout mContainer;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
            mContainer = (FrameLayout) itemView.findViewById(R.id.photo_container);
        }
    }

    public interface OnPhotoClickListener {
        void onPhotoClick(View v, int position, String arg);
    }
}
