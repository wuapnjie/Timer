package com.iec.dwx.timer.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Views.CardFlipLayout;
import com.iec.dwx.timer.Views.PickView;

import java.util.Calendar;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TimeActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String PREFERENCE_NAME = "TimeActivity";
    public static final String PREFERENCE_KEY_GRADE = "grade";  //SharePreferences的key，储存年级
    public static final String PREFERENCE_KEY_GRADE_EMPTY = "empty";
    @Bind(R.id.card_container)
    CardFlipLayout mCardContainer;
    @Bind(R.id.pick_view)
    PickView mPickView;
    @Bind(R.id.menu_bar)
    Toolbar mMenuBar;
    @Bind(R.id.tv_card_one_head)
    TextView mTextView;

    private String[] mValues;

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
        mValues = this.getResources().getStringArray(R.array.pick_values);
        mPickView.setValues(mValues);
        mMenuBar.inflateMenu(R.menu.menu_time);

        Observable.just(PREFERENCE_KEY_GRADE)
                .map(s -> getPreferences(s, PREFERENCE_KEY_GRADE_EMPTY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    if (value.equals(PREFERENCE_KEY_GRADE_EMPTY)) {
                        findViewById(R.id.pick_container).setVisibility(View.VISIBLE);
                        mPickView.setOnConfirmBtnClickListener(view -> dismiss());
                    } else {
                        calculateTime(value);
                    }
                });
    }

    private void initListener() {
        findViewById(R.id.btn_time_now).setOnClickListener(v -> mCardContainer.flip());
        findViewById(R.id.btn_time_leave).setOnClickListener(v -> mCardContainer.flip());

        mMenuBar.setOnMenuItemClickListener(this);
    }

    /**
     * 选择完成，使PickView消失
     */
    private void dismiss() {
        //TODO 逻辑未想好,还有动画效果
        if (!mPickView.getCheckedValue().equals(PickView.VALUE_EMPTY)) {
            findViewById(R.id.pick_container).setVisibility(View.GONE);
        }
        Toast.makeText(this, mPickView.getCheckedValue(), Toast.LENGTH_SHORT).show();

        savePreferences(PREFERENCE_KEY_GRADE, mPickView.getCheckedValue());
    }

    /**
     * 储存SharePreference
     *
     * @param key        键
     * @param preference 值
     */
    private void savePreferences(String key, String preference) {
        getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putString(key, preference).commit();
    }

    private String getPreferences(String key, String defaultValue) {
        return getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(key, defaultValue);
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
                intent.setClass(this, AboutActivity.class);
                startActivity(intent);
                return true;
        }
        intent.putExtra(MainActivity.INTENT_KEY_PAGE, flag);
        startActivity(intent);
        return true;
    }

    /**
     * 重新设置，令PickView出现
     */
    private void showPick() {
        findViewById(R.id.pick_container).setVisibility(View.VISIBLE);
        mPickView.setOnConfirmBtnClickListener(view -> dismiss());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        System.out.println("key->" + key);
        calculateTime(sharedPreferences.getString(key, PREFERENCE_KEY_GRADE_EMPTY));
    }

    private void calculateTime(String value) {
        int i;
        for (i = 0; i < mValues.length; i++) {
            if (value.equals(mValues[i])) {
                break;
            }
        }
        Observable.just(i)
                .map(integer -> calculateDay(integer))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(day -> {
                    mTextView.setText(String.format(this.getString(R.string.card_one_head), day));
                    ((TextView) findViewById(R.id.tv_card_one_cell_1)).setText(String.format("* %s月", (day / 30)));
                    ((TextView) findViewById(R.id.tv_card_one_cell_2)).setText(String.format("* %s个日夜", day));
                    ((TextView) findViewById(R.id.tv_card_one_cell_3)).setText(String.format("* %s小时", day * 24));
                    ((TextView) findViewById(R.id.tv_card_one_cell_4)).setText(String.format("* %s分钟", day * 24 * 60));

                    ((TextView) findViewById(R.id.tv_card_two_head)).setText(String.format(this.getString(R.string.card_two_head), ((int) ((1400 - day) / 14 * 100)) / 100f + "%"));
                    ((TextView) findViewById(R.id.tv_card_two_cell_1)).setText(String.format("* 在教室上%s次自习", (int) ((1400 - day) / 7) * 5));
                    ((TextView) findViewById(R.id.tv_card_two_cell_2)).setText(String.format("* 在寝室睡%s个觉", (int) ((1400 - day) / 1)));
                    ((TextView) findViewById(R.id.tv_card_two_cell_3)).setText(String.format("* 在图书馆看%s本书", (int) ((1400 - day) / 7) * 2));
                    ((TextView) findViewById(R.id.tv_card_two_cell_4)).setText(String.format("* 在食堂吃%s次饭", (int) ((1400 - day) / 1) * 3));
                });

    }

    private float calculateDay(int i) {
        Calendar calendar = Calendar.getInstance();
        long now = System.currentTimeMillis();
        int year = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, year - i);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long start = calendar.getTimeInMillis();
        return (now - start) / 86400000f;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_time, menu);
        return true;
    }
}
