package com.iec.dwx.timer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.iec.dwx.timer.Adapters.NotificationAdapter;
import com.iec.dwx.timer.Beans.Notification;
import com.iec.dwx.timer.Beans.User;
import com.iec.dwx.timer.Beans.WishComment;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Flying SnowBean on 2015/12/16.
 */
public class NotificationActivity extends BaseActivity {
    private final String TAG = NotificationActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private NotificationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        pullNotification();
    }

    private void pullNotification() {
        User user = BmobUser.getCurrentUser(this, User.class);
        BmobQuery<WishComment> query = new BmobQuery<>();
        query.setLimit(20);
        query.include("wish,wish.author");
        query.findObjects(this, new FindListener<WishComment>() {
            @Override
            public void onSuccess(List<WishComment> list) {
                List<Notification> data = new ArrayList<>();
//                Toast.makeText(NotificationActivity.this, "haha->" + (list.get(0).getWish().getAuthor() == null), Toast.LENGTH_SHORT).show();
                for (WishComment comment : list) {
                    if (comment.getWish().getAuthor().getObjectId().equals(user.getObjectId())) {
                        Notification notification = new Notification();
                        notification.setType(1);
                        notification.setWish(comment.getWish());
                        data.add(notification);
                    }
                }

                mAdapter.setData(data);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(NotificationActivity.this, "Failure,please retry!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_notification);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NotificationAdapter(this);
        mAdapter.setOnFeedClickListener((view, position) -> {
            Intent intent = new Intent(this, WishCommentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("wish", mAdapter.getData().get(position).getWish());

            intent.putExtras(bundle);
            startActivity(intent);
        });

        mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_notification);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected int getEdgeSize() {
        return Utils.dp2px(200);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_notification;
    }
}
