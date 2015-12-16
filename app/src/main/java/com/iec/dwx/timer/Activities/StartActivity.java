package com.iec.dwx.timer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.iec.dwx.timer.Beans.User;
import com.iec.dwx.timer.R;

import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import pl.droidsonroids.gif.GifDrawable;

/**
 * 开始动画界面
 */
public class StartActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    GifDrawable mGifDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        try {
            mGifDrawable = new GifDrawable(getResources(), R.drawable.time_animated);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((ImageView) findViewById(R.id.iv_introduce)).setImageDrawable(mGifDrawable);

        if (BmobUser.getCurrentUser(this) == null) {
            TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);

            User user = new User();
            user.setUsername(telephonyManager.getDeviceId());
            user.setPassword(telephonyManager.getDeviceId());
            user.signUp(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    user.login(StartActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "onSuccess: 登陆成功！");

                            (new Handler()).postDelayed(() -> {
                                startActivity(new Intent(StartActivity.this, TimeActivity.class));
                                finish();
                            }, 4000);

                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.d(TAG, "onFailure: 登陆失败");
                            Toast.makeText(StartActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(int i, String s) {
                    Log.d(TAG, "onFailure: 注册失败");
                    Toast.makeText(StartActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            (new Handler()).postDelayed(() -> {
                startActivity(new Intent(this, TimeActivity.class));
                finish();
            }, 4000);

        }


    }

}
