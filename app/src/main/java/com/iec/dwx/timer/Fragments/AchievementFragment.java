package com.iec.dwx.timer.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.iec.dwx.timer.Activities.EditAchievementActivity;
import com.iec.dwx.timer.Activities.MainActivity;
import com.iec.dwx.timer.Adapters.AchievementAdapter;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.DBHelper;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AchievementFragment extends Fragment implements Toolbar.OnMenuItemClickListener {
    private final String TAG = AchievementFragment.class.getSimpleName();
    public static final int SELECTED_NONE = -1;

    private RecyclerView.LayoutManager mManager;
    private AchievementAdapter mAdapter;
    private PopupWindow mPopupWindow;
    private RecyclerView mRecyclerView;

    //被选中成就的位置,
    private int mSelectedPosition = SELECTED_NONE;

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
        mAdapter = new AchievementAdapter(getActivity());
        mManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_achievement);

        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingListener();

        //初始化PopupWindow
        Observable.just(R.layout.popup_view)
                .map(integer -> LayoutInflater.from(getActivity()).inflate(integer, null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view1 -> initializePopupWindow(view1));

    }

    /**
     * 设置监听事件
     */
    private void settingListener() {
        if (getView() == null) return;

        ((Toolbar) getView().findViewById(R.id.toolbar_achievement)).inflateMenu(R.menu.menu_achievement);
        ((Toolbar) getView().findViewById(R.id.toolbar_achievement)).setOnMenuItemClickListener(this);
        ((Toolbar) getView().findViewById(R.id.toolbar_achievement)).setNavigationOnClickListener(v -> getActivity().onBackPressed());

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d(TAG, "newState->" + newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                        cancelSelectedShader(mSelectedPosition);
                        mSelectedPosition = SELECTED_NONE;
                    }
                }
            }
        });

        mAdapter.setOnAchievementLongClickListener((v, position) -> {
            mSelectedPosition = position;
            Log.d(TAG, "after long click,the mSelectedPosition->" + mSelectedPosition);
            showPopupWindow();
        });
        mAdapter.setOnAchievementClickListener((v, position) -> {
            mSelectedPosition = SELECTED_NONE;
            Log.d(TAG, "after click,the mSelectedPosition->" + mSelectedPosition);
            dismissPopupWindow();
        });

    }

    /**
     * 取消Item 的 选中状态
     */

    private void cancelSelectedShader(int position) {
        if (position != SELECTED_NONE && mManager.getChildAt(position) != null) {
            mManager.getChildAt(position).findViewById(R.id.achievement_shader).setVisibility(View.GONE);
        }
    }

    /**
     * 初始化popupWindow
     *
     * @param view 视图
     */
    private void initializePopupWindow(View view) {
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btnDelete = (Button) view.findViewById(R.id.btn_popup_delete);

        btnDelete.setOnClickListener(v -> {
            deleteAchievement();
            Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();
        });

    }

    /**
     * 删除Item
     */
    private void deleteAchievement() {
        if (mSelectedPosition == SELECTED_NONE) return;
        if (mPopupWindow.isShowing()) mPopupWindow.dismiss();


        DBHelper.getInstance(getActivity()).deleteBean(DBHelper.DB_TABLE_ACHIEVEMENT, mAdapter.getData().get(mSelectedPosition));

        mAdapter.deleteItem(mSelectedPosition);

        refreshData();

        cancelSelectedShader(mSelectedPosition);

        mSelectedPosition = SELECTED_NONE;
    }

    @Override
    public void onResume() {
        super.onResume();
        //异步刷新数据
        refreshData();

        //取消滑动返回
        ((MainActivity) getActivity()).getSwipeBackLayout().setEdgeSize(0);
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        Observable.just(DBHelper.DB_TABLE_ACHIEVEMENT)
                .map(s -> DBHelper.getInstance(getActivity()).getAllBeans(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commonBeans -> {
                    mAdapter.obtainData(commonBeans);
                    if (getView() != null) {
                        if (mAdapter.getItemCount() == 0) {
                            getView().findViewById(R.id.achievement_empty_container).setVisibility(View.VISIBLE);
                        } else {
                            getView().findViewById(R.id.achievement_empty_container).setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     * 使PopupWindow消失
     */
    private void dismissPopupWindow() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            cancelSelectedShader(mSelectedPosition);
        }
    }

    /**
     * 长按事件
     * 显示PopupWindow
     */
    private void showPopupWindow() {
        mPopupWindow.showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
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

    /**
     * 跳转至添加成就
     */
    private void intent2Add() {
        startActivity(new Intent(getActivity(), EditAchievementActivity.class));
        getActivity().overridePendingTransition(R.anim.activity_time_enter, R.anim.activity_time_exit);
    }

}
