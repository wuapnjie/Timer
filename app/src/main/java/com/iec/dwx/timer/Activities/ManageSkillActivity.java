package com.iec.dwx.timer.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.iec.dwx.timer.Adapters.ManageSkillAdapter;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.DBHelper;
import com.iec.dwx.timer.Utils.Utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ManageSkillActivity extends BaseActivity{
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mManager;
    ManageSkillAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        Observable.just(DBHelper.DB_TABLE_SKILL)
                .map(s->DBHelper.getInstance(this).getAllBeans(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commonBeans -> mAdapter.obtainData(commonBeans));

    }

    @Override
    protected int getEdgeSize() {
        return Utils.dp2px(200);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_manage_skill;
    }

    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_manage_skill);
        mManager = new LinearLayoutManager(this);
        mAdapter = new ManageSkillAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mManager);

        ((Toolbar)findViewById(R.id.toolbar_manage_skill)).setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_time_enter, R.anim.activity_time_exit);
    }
}
