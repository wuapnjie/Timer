package com.iec.dwx.timer.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.ImageUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditAchievementActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_achievement);
        initial();
        initListener();
    }

    private void initial() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_edit_achievement);
        mToolbar.inflateMenu(R.menu.menu_edit_achievement);

//        String arg = getIntent().getStringExtra(PickPhotoActivity.INTENT_KEY_ARG);
//
//        Observable.just(arg)
//                .map(s -> ImageUtils.decodeFromFile(arg, 200, 200))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(bitmap -> ((ImageView) findViewById(R.id.iv_preview)).setImageBitmap(bitmap));

    }

    private void initListener() {
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            update();
            return true;
        });
    }

    private void update() {
    }


}
