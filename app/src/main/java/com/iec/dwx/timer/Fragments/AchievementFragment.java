package com.iec.dwx.timer.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iec.dwx.timer.Activities.EditAchievementActivity;
import com.iec.dwx.timer.Activities.PickPhotoActivity;
import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.CacheManager.SMemoryCacheManager;
import com.iec.dwx.timer.Utils.DBHelper;
import com.iec.dwx.timer.Utils.ImageUtils;
import com.iec.dwx.timer.Utils.ScreenSizeUtils;
import com.iec.dwx.timer.Utils.Utils;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AchievementFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private RecyclerView.LayoutManager mManager;
    private AchievementAdapter mAdapter;

    public static AchievementFragment newInstance() {
        return new AchievementFragment();
    }

    public AchievementFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_achievement, container, false);
        mAdapter = new AchievementAdapter();
        mManager = new LinearLayoutManager(getActivity());
        ((RecyclerView) rootView.findViewById(R.id.rv_achievement)).setLayoutManager(mManager);
        ((RecyclerView) rootView.findViewById(R.id.rv_achievement)).setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getView() == null) return;
        ((Toolbar) getView().findViewById(R.id.toolbar_achievement)).inflateMenu(R.menu.menu_achievement);
        ((Toolbar) getView().findViewById(R.id.toolbar_achievement)).setOnMenuItemClickListener(this);
        ((Toolbar) getView().findViewById(R.id.toolbar_achievement)).setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    @Override
    public void onResume() {
        super.onResume();
        Observable.just(DBHelper.DB_TABLE_ACHIEVEMENT)
                .map(s -> DBHelper.getInstance(getActivity()).getAllBeans(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commonBeans -> {
                    mAdapter.obtainData(commonBeans);
                });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_achievement_play:
                break;
            case R.id.menu_achievement_add:
//                linkToPickPhoto();
                intent2Add();
                break;
        }
        return false;
    }

    private void intent2Add() {
        startActivity(new Intent(getActivity(), EditAchievementActivity.class));
    }

    private void linkToPickPhoto() {
        startActivity(new Intent(getActivity(), PickPhotoActivity.class));
    }

    public class AchievementAdapter extends RecyclerView.Adapter<AchievementViewHolder> {
        private List<CommonBean> mData;

        public void obtainData(List<CommonBean> data) {
            this.mData = data;
            notifyDataSetChanged();
        }

        @Override
        public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.achivement_item, parent, false);
            return new AchievementViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(AchievementViewHolder holder, int position) {
            holder.mImageView.setLayoutParams(new LinearLayout.LayoutParams(ScreenSizeUtils.getWidth(getActivity()),ScreenSizeUtils.getWidth(getActivity())));
            holder.mTextView.setText(mData.get(position).getContent());
            Observable.just(mData.get(position).getPicture())
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
                        SMemoryCacheManager.getInstance().putBitmap(mData.get(position).getPicture(), bitmap);
                    });
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }
    }

    public static class AchievementViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;
        CardView mContainer;

        public AchievementViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_achievement_head);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_achievement_photo);
            mContainer = (CardView) itemView.findViewById(R.id.achievement_container);
        }
    }
}
