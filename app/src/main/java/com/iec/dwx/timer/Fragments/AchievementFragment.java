package com.iec.dwx.timer.Fragments;


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
import android.widget.TextView;

import com.iec.dwx.timer.R;

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
        if (savedInstanceState == null) {
            mAdapter = new AchievementAdapter();
            mManager = new LinearLayoutManager(getActivity());
            ((RecyclerView) rootView.findViewById(R.id.rv_achievement)).setLayoutManager(mManager);
            ((RecyclerView) rootView.findViewById(R.id.rv_achievement)).setAdapter(mAdapter);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getView() == null) return;
        ((Toolbar) getView().findViewById(R.id.toolbar_achievement)).inflateMenu(R.menu.menu_achievement);
        ((Toolbar) getView().findViewById(R.id.toolbar_achievement)).setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

        }
        return false;
    }

    public class AchievementAdapter extends RecyclerView.Adapter<AchievementViewHolder> {

        @Override
        public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.achivement_item, parent, false);
            return new AchievementViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(AchievementViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return 10;
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
