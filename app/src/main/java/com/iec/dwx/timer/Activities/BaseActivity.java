package com.iec.dwx.timer.Activities;

import android.os.Bundle;
import android.util.Log;

import com.iec.dwx.timer.BuildConfig;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 基础Activity，继承自SwipeBackActivity，提供外部修改接口
 * Created by Flying SnowBean on 2015/10/2.
 */
public abstract class BaseActivity extends SwipeBackActivity {
    protected final String TAG = this.getClass().getSimpleName();

    protected SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreate");
        }
        setContentView(getLayoutID());
        ButterKnife.bind(this);
        mSwipeBackLayout = getSwipeBackLayout();

        mSwipeBackLayout.setEdgeSize(getEdgeSize());

        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    /**
     * 外部设置边缘大小接口
     *
     * @return 想设置的值
     */
    protected abstract int getEdgeSize();

    /**
     * 外部设置布局文件接口
     *
     * @return 布局文件ID
     */
    protected abstract int getLayoutID();

    @Override
    protected void onStart() {
        super.onStart();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStart");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onResume");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onPause");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStop");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onDestroy");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "SaveInstanceState");
        }
    }

}
