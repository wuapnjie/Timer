package com.iec.dwx.timer.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iec.dwx.timer.Activities.ManageSkillActivity;
import com.iec.dwx.timer.Beans.CommonBean;
import com.iec.dwx.timer.Beans.SkillBean;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.DataBaseHelper.DBHelper;
import com.iec.dwx.timer.Utils.ScreenSizeUtils;
import com.iec.dwx.timer.Views.ViewDragHelperLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SkillFragment extends Fragment implements Toolbar.OnMenuItemClickListener {
    private List<TextView> viewList = new ArrayList<TextView>();
    private FrameLayout addView = null;
    private ViewDragHelperLayout viewDragHelperLayout = null;
    private InputMethodManager imm = null;
    private Thread td = null;


    public static SkillFragment newInstance() {
        return new SkillFragment();
    }

    public SkillFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_skill, container, false);
        addView = (FrameLayout) rootView.findViewById(R.id.my_skill_add_view);
        addView.setVisibility(View.GONE);
        viewDragHelperLayout = (ViewDragHelperLayout) rootView.findViewById(R.id.fragment_my_skill_viewDragHelperLayout);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getView() == null) return;
        ((Toolbar) getView().findViewById(R.id.toolbar_my_skill)).inflateMenu(R.menu.menu_my_skill);
        ((Toolbar) getView().findViewById(R.id.toolbar_my_skill)).setOnMenuItemClickListener(this);
        ((Toolbar) getView().findViewById(R.id.toolbar_my_skill)).setNavigationOnClickListener(v -> getActivity().onBackPressed());
        getView().findViewById(R.id.my_skill_add_btn_sure).setOnClickListener(v -> onAddButtonSureOnClicked((EditText) getView().
                findViewById(R.id.my_skill_add_editText)));
        getView().findViewById(R.id.my_skill_add_btn_cencel).setOnClickListener(v -> onAddButtonCancelOnClicked(
                (EditText) getView().findViewById(R.id.my_skill_add_editText)));

        List<CommonBean> Data = DBHelper.getInstance(getContext()).getAllBeans(DBHelper.DB_TABLE_SKILL);

    }


    @Override
    public void onResume() {
        super.onResume();
        Observable.just(DBHelper.DB_TABLE_SKILL)
                .map(s -> DBHelper.getInstance(getActivity()).getAllBeans(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commonBeans -> {
                    if (getView() != null) {
                        viewDragHelperLayout.removeAllViews();
                        //如果数据为空，则显示提示
                        if (commonBeans == null || commonBeans.size() == 0) {
                            getView().findViewById(R.id.skill_empty_container).setVisibility(View.VISIBLE);
                        } else {
                            getView().findViewById(R.id.skill_empty_container).setVisibility(View.GONE);

                            TextView textView;
                            for (CommonBean commonBean : commonBeans) {
                                textView = getTextView(commonBean, commonBean.getID());
                                viewList.add(textView);
                                viewDragHelperLayout.addView(textView);
                            }
                        }
                    }
                });
        Log.d("haha", "onResume");
    }

    private TextView getTextView(CommonBean commonBean, int id) {

        int marginLeft = Integer.parseInt(commonBean.getTime());
        int marginTop = Integer.parseInt(commonBean.getPicture());

        TextView textView = new TextView(getContext());

        textView.setTag(R.id.tag_zero, id);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(marginLeft, marginTop, 0, 0);
        textView.setLayoutParams(lp);
        textView.setTextColor(Color.BLACK);
        textView.setText(commonBean.getContent());
        textView.setTextSize(24f);

        return textView;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_my_skill_add:
                addView.setVisibility(View.VISIBLE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_HIDDEN);
//                viewDragHelperLayout.setBackgroundResource(R.color.black_overlay);
                break;
            case R.id.menu_my_skill_edit:

                startActivity(new Intent(getActivity(), ManageSkillActivity.class));
                getActivity().overridePendingTransition(R.anim.activity_time_enter, R.anim.activity_time_exit);
                break;
        }
        return false;
    }

    //新建确定键点击
    private void onAddButtonSureOnClicked(EditText editText) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(getContext(), "保存内容不能为空", Toast.LENGTH_SHORT).show();
        } else {
            addSureBtnSaveData(editText);
        }
    }

    //新建取消键点击
    private void onAddButtonCancelOnClicked(EditText editText) {
        editText.setText("");
        addView.setVisibility(View.GONE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        System.out.println("取消保存");
    }

    //存储数据
    private void addSureBtnSaveData(EditText editText) {

        int margin_left = (int) ((Math.random() * ScreenSizeUtils.getWidth(getContext()) * 3 / 4) + (ScreenSizeUtils.getWidth(getContext()) / 8));
        int margin_top = (int) ((Math.random() * ScreenSizeUtils.getHeight(getContext()) * 3 / 4) + (ScreenSizeUtils.getWidth(getContext()) / 8));

        SkillBean skillBean = new SkillBean();
        skillBean.setContent(editText.getText().toString());
        skillBean.setMarginLeft(margin_left);
        skillBean.setMarginTop(margin_top);
        editText.setText("");

        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

        int id = DBHelper.getInstance(getContext()).addBeanToDatabase(DBHelper.DB_TABLE_SKILL, skillBean);

        addView.setVisibility(View.GONE);

        //使技能为空时的View不可见
        if (getView() != null && getView().findViewById(R.id.skill_empty_container).getVisibility() == View.VISIBLE) {
            getView().findViewById(R.id.skill_empty_container).setVisibility(View.GONE);
        }

        viewDragHelperLayout.addView(getTextView(skillBean, id));

        Toast.makeText(getContext(), "成功保存", Toast.LENGTH_SHORT).show();
    }

}
