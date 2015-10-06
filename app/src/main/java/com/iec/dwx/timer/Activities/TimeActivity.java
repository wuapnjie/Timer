package com.iec.dwx.timer.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Views.CardFlipLayout;
import com.iec.dwx.timer.Views.PickView;

import butterknife.Bind;

public class TimeActivity extends BaseActivity {

    public static final String PREFERENCE_KEY_GRADE = "grade";  //SharePreferences的key，储存年级
    @Bind(R.id.card_container)
    CardFlipLayout mCardContainer;
    @Bind(R.id.pick_view)
    PickView mPickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        initListener();

    }

    private void initialize() {
        mPickView.setValues(this.getResources().getStringArray(R.array.pick_values));
    }

    private void initListener() {
        findViewById(R.id.btn_time_now).setOnClickListener(v -> mCardContainer.flip());
        findViewById(R.id.btn_time_leave).setOnClickListener(v -> mCardContainer.flip());

        mPickView.setOnConfirmBtnClickListener(view -> dismiss());
    }

    private void dismiss() {
        //TODO 逻辑未想好,还有动画效果
        if (!mPickView.getCheckedValue().equals(PickView.VALUE_EMPTY)) {
            findViewById(R.id.pick_container).setVisibility(View.GONE);
        }
        Toast.makeText(this, mPickView.getCheckedValue(), Toast.LENGTH_SHORT).show();
    }

    private void savePreferences(String key, String preference) {
        getPreferences(Context.MODE_PRIVATE).edit().putString(key, preference).commit();
    }


    @Override
    protected int getEdgeSize() {
        return 0;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_time;
    }

}
