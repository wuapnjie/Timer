package com.iec.dwx.timer.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iec.dwx.timer.R;

public class AchievementFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_achievement, container, false);
    }


}
