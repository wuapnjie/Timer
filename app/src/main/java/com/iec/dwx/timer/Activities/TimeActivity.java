package com.iec.dwx.timer.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Views.CardFlipLayout;
import com.iec.dwx.timer.Views.PickView;

import butterknife.Bind;

public class TimeActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String PREFERENCE_NAME = "TimeActivity";
    public static final String PREFERENCE_KEY_GRADE = "grade";  //SharePreferences的key，储存年级
    @Bind(R.id.card_container)
    CardFlipLayout mCardContainer;
    @Bind(R.id.pick_view)
    PickView mPickView;
    @Bind(R.id.menu_bar)
    Toolbar mMenuBar;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        initListener();

    }

    private void initialize() {
        mPickView.setValues(this.getResources().getStringArray(R.array.pick_values));
        mMenuBar.inflateMenu(R.menu.menu_time);
    }

    private void initListener() {
        findViewById(R.id.btn_time_now).setOnClickListener(v -> mCardContainer.flip());
        findViewById(R.id.btn_time_leave).setOnClickListener(v -> mCardContainer.flip());

        mPickView.setOnConfirmBtnClickListener(view -> dismiss());
        mMenuBar.setOnMenuItemClickListener(this);
    }

    private void dismiss() {
        //TODO 逻辑未想好,还有动画效果
        if (!mPickView.getCheckedValue().equals(PickView.VALUE_EMPTY)) {
            findViewById(R.id.pick_container).setVisibility(View.GONE);
        }
        Toast.makeText(this, mPickView.getCheckedValue(), Toast.LENGTH_SHORT).show();

        savePreferences(PREFERENCE_KEY_GRADE, mPickView.getCheckedValue());
    }

    private void savePreferences(String key, String preference) {
        getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putString(key, preference).commit();
    }


    @Override
    protected int getEdgeSize() {
        return 0;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_time;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        int flag = 0;
        switch (item.getItemId()) {
            case R.id.menu_time_wish:
                flag = 0;
                break;
            case R.id.menu_time_achievement:
                flag = 1;
                break;
            case R.id.menu_time_skill:
                flag = 2;
                break;
            case R.id.menu_time_reset:
                showPick();
                return true;
            case R.id.menu_time_about:

                break;
        }
        intent.putExtra(MainActivity.INTENT_KEY_PAGE, flag);
        startActivity(intent);
        return true;
    }

    private void showPick() {
        findViewById(R.id.pick_container).setVisibility(View.VISIBLE);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        System.out.println("onSharedPreferenceChanged");
    }
}
