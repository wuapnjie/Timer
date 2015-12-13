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
import com.iec.dwx.timer.Adapters.AchievementAdapter;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Views.ShareDialog;
import com.iec.dwx.timer.Utils.DataBaseHelper.DBHelper;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AchievementFragment extends Fragment implements Toolbar.OnMenuItemClickListener, PlatformActionListener {
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
        ShareSDK.initSDK(getActivity());
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
        Button btnShare = (Button) view.findViewById(R.id.btn_popup_share);

        btnDelete.setOnClickListener(v -> {
            deleteAchievement();
            Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();
        });

        btnShare.setOnClickListener(v -> showShare());

    }

    /**
     * 显示分享界面
     */
    private void showShare() {
//        OnekeyShare oks = new OnekeyShare();
//        oks.disableSSOWhenAuthorize();
//        oks.setDialogMode();
//        oks.setText(mAdapter.getData().get(mSelectedPosition).getContent());
//        oks.setImagePath(mAdapter.getData().get(mSelectedPosition).getPicture());
//        oks.setTitle(getString(R.string.app_name));
//        oks.setCallback(this);
//        oks.setTitleUrl("http://sharesdk.cn");
//        oks.show(getActivity());
        if (mPopupWindow.isShowing()) mPopupWindow.dismiss();
        ShareDialog shareDialog = new ShareDialog(getActivity());
        shareDialog.setCancelButtonOnClickListener(v -> shareDialog.dismiss());
        shareDialog.setOnItemClickListener((parent, view, position, id) -> {
            HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);
            if (getString(R.string.platform_weibo).equals(item.get("Text"))) {
                Toast.makeText(getActivity(), "you choose " + item.get("Text"), Toast.LENGTH_SHORT).show();
                Platform.ShareParams shareParams = new Platform.ShareParams();
                shareParams.setTitle("分享到新浪微博");
                shareParams.setText(mAdapter.getData().get(mSelectedPosition).getContent());
                shareParams.setImagePath(mAdapter.getData().get(mSelectedPosition).getPicture());

                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//                weibo.SSOSetting(true);
                weibo.setPlatformActionListener(this);
                weibo.share(shareParams);
            }

            if (getString(R.string.platform_qq).equals(item.get("Text"))) {
                Toast.makeText(getActivity(), "you choose " + item.get("Text"), Toast.LENGTH_SHORT).show();
                Platform.ShareParams shareParams = new Platform.ShareParams();
                shareParams.setTitle("分享到QQ");
                shareParams.setText(mAdapter.getData().get(mSelectedPosition).getContent());
                shareParams.setImagePath(mAdapter.getData().get(mSelectedPosition).getPicture());
                shareParams.setTitleUrl("http://www.baidu.com/");

                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.setPlatformActionListener(this);
                qq.share(shareParams);
            }


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

    /**
     * ShareSDK分享的回调，回调在子行程中进行
     *
     * @param platform 对应的平台
     * @param i
     * @param hashMap
     */
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Log.d(TAG, "onComplete" + platform.getName());
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.d(TAG, platform.getName() + "throwable->" + throwable.getMessage()+ " status->"+i);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Log.d(TAG, platform.getName()+" onCancel");
    }


}
