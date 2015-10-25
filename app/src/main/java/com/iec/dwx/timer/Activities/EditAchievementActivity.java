package com.iec.dwx.timer.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.iec.dwx.timer.Beans.AchievementBean;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.DBHelper;
import com.iec.dwx.timer.Utils.ImageUtils;
import com.iec.dwx.timer.Utils.ScreenSizeUtils;
import com.iec.dwx.timer.Utils.Utils;

import java.io.File;
import java.util.Date;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 编辑上传Achievement的Activity
 */
public class EditAchievementActivity extends BaseActivity {
    public static final int REQUEST_CAPTURE = 0;
    public static final int REQUEST_PICK = 1;
    public static final int REQUEST_CROP = 2;

    private Toolbar mToolbar;
    private EditText mEditText;

    private PopupMenu mPopupMenu;

    private boolean isPopupMenuShow = false;

    private String mPath;
    private boolean hasPicture = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initial();
        initListener();
    }

    @Override
    protected int getEdgeSize() {
        return 0;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_edit_achievement;
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
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
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

    /**
     * 从相册挑选照片
     */
    private void toPick() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK);
    }

    /**
     * 拍照
     */
    private void toTake() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = ImageUtils.createFile(this);
        if (file != null)
            mPath = file.getAbsolutePath();
        System.out.println(mPath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, REQUEST_CAPTURE);
        overridePendingTransition(R.anim.activity_time_enter, R.anim.activity_time_exit);
    }

    private void showPickOrTake() {
        mPopupMenu.show();
        isPopupMenuShow = true;
    }

    private void update() {
        if (mEditText.getText().toString().equals("")) {
            Toast.makeText(this, "请输入成就", Toast.LENGTH_SHORT).show();
        } else if (!hasPicture) {
            Toast.makeText(this, "请插入照片", Toast.LENGTH_SHORT).show();
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
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_PICK:
                Uri imageUri = data.getData();
                Log.d(TAG, "imageUri->" + imageUri);
                toCrop(imageUri);
                break;
            case REQUEST_CAPTURE:
                Uri photoUri = Uri.fromFile(new File(mPath));
                Log.d(TAG, "photoUri->" + photoUri);
                toCrop(photoUri);
                break;
            case REQUEST_CROP:
                hasPicture = true;
                Observable.just(mPath)
                        .map(s -> ImageUtils.decodeFromFile(s, 200, 200))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bitmap -> ((ImageView) findViewById(R.id.iv_pick_photo)).setImageBitmap(bitmap));
                hasPicture = true;
                break;
        }


    }

    /**
     * 剪裁照片
     *
     * @param imageUri 照片Uri
     */
    private void toCrop(Uri imageUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", ScreenSizeUtils.getWidth(this));
        intent.putExtra("outputY", ScreenSizeUtils.getWidth(this));
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);

        File file = ImageUtils.createFile(this);
        if (file != null) {
            mPath = file.getAbsolutePath();
        } else {
            mPath = imageUri.getPath();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mPath)));
        startActivityForResult(intent, REQUEST_CROP);
        overridePendingTransition(R.anim.activity_time_enter, R.anim.activity_time_exit);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_time_enter, R.anim.activity_time_exit);
    }
}
