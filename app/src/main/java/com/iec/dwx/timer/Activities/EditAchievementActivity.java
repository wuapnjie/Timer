package com.iec.dwx.timer.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.iec.dwx.timer.Beans.AchievementBean;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.DBHelper;
import com.iec.dwx.timer.Utils.ImageUtils;
import com.iec.dwx.timer.Utils.Utils;

import java.io.File;
import java.util.Date;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditAchievementActivity extends AppCompatActivity {
    public static final int REQUEST_CAPTURE = 0;
    public static final int REQUEST_PICK = 1;

    private Toolbar mToolbar;
    private EditText mEditText;

    private PopupMenu mPopupMenu;

    private boolean isPopupMenuShow = false;

    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_achievement);
        initial();
        initListener();
    }

    private void initial() {
        mEditText = (EditText) findViewById(R.id.et_write_achievement);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_edit_achievement);
        mToolbar.inflateMenu(R.menu.menu_edit_achievement);

        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        mPopupMenu = new PopupMenu(this, findViewById(R.id.iv_pick_photo));
        mPopupMenu.inflate(R.menu.menu_pop_pick_photo);

    }

    private void initListener() {
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            update();
            return true;
        });

        (findViewById(R.id.iv_pick_photo)).setOnClickListener(v -> showPickOrTake());

        mPopupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_pick_photo:
                    toPick();
                    break;
                case R.id.menu_take_photo:
                    toTake();
                    break;
            }
            return true;
        });
    }

    private void toPick() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK);
    }

    private void toTake() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = ImageUtils.createFile(this);
        mPath = file.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    private void showPickOrTake() {
        mPopupMenu.show();
        isPopupMenuShow = true;
        Toast.makeText(this, "pop", Toast.LENGTH_SHORT).show();
    }

    private void update() {
        if (mEditText.getText().toString().equals("")) {
            Toast.makeText(this, "请输入成就", Toast.LENGTH_SHORT).show();
        } else {
            AchievementBean bean = new AchievementBean();
            bean.setPicture(mPath);
            bean.setTime(Utils.formatTime(new Date()));
            bean.setContent(mEditText.getText().toString());
            Observable.just(bean)
                    .map(bean1 -> DBHelper.getInstance(this).addBeanToDatabase(DBHelper.DB_TABLE_ACHIEVEMENT, bean))
                    .subscribeOn(Schedulers.io())
                    .subscribe();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (isPopupMenuShow) {
            mPopupMenu.dismiss();
            isPopupMenuShow = false;
        } else
            super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_PICK:
                Uri uri = data.getData();
                Observable.just(uri)
                        .map(uri1 -> getContentResolver().query(uri1, null, null, null, null))
                        .map(cursor -> {
                            if (cursor.moveToFirst())
                                mPath = cursor.getString(1);
                            return mPath;
                        })
                        .map(s -> ImageUtils.decodeFromFile(s, 200, 200))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bitmap -> ((ImageView) findViewById(R.id.iv_pick_photo)).setImageBitmap(bitmap));
                break;
            case REQUEST_CAPTURE:
                Observable.just(mPath)
                        .map(s -> ImageUtils.decodeFromFile(mPath, 200, 200))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bitmap -> ((ImageView) findViewById(R.id.iv_pick_photo)).setImageBitmap(bitmap));
                break;
            default:
                break;
        }
    }
}
