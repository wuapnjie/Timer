package com.iec.dwx.timer.Activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iec.dwx.timer.Adapters.WishCommentAdapter;
import com.iec.dwx.timer.Beans.OthersWish;
import com.iec.dwx.timer.Beans.WishComment;
import com.iec.dwx.timer.BuildConfig;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.Utils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Flying SnowBean on 2015/12/13.
 */
public class WishCommentActivity extends BaseActivity {
    private final String TAG = WishCommentActivity.class.getSimpleName();
    private FrameLayout mContainer;
    private RecyclerView mRecyclerView;
    private ImageView mIvLike;
    private ImageView mIvComment;
    private TextView mTvLikeNum;
    private AppCompatEditText mEditText;
    private ImageView mIvSend;
    private OthersWish mOthersWish;
    private WishCommentAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        mContainer.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mContainer.getViewTreeObserver().removeOnPreDrawListener(this);
                animateLayout();
                return true;
            }
        });

        if (getIntent().getExtras() != null) {
            mOthersWish = (OthersWish) getIntent().getExtras().getSerializable("wish");
            ((TextView) findViewById(R.id.tv_content)).setText(mOthersWish.getContent());
            ((TextView) findViewById(R.id.tv_time)).setText(mOthersWish.getCreatedAt());
            mTvLikeNum.setText(mOthersWish.getLikeNumber() + " likes");
        }

        initListener();

        loadData();

    }

    private void animateLayout() {
        int[] location = getIntent().getExtras().getIntArray("location");

        mContainer.setScaleY(0.1f);
        mContainer.setPivotY(location[1]);

        mContainer.animate()
                .scaleY(1)
                .setDuration(400)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    private void loadData() {
        BmobQuery<WishComment> query = new BmobQuery<>("WishComment");
        query.addWhereEqualTo("wish", mOthersWish);
        query.findObjects(this, new FindListener<WishComment>() {
            @Override
            public void onSuccess(List<WishComment> list) {
                if (BuildConfig.DEBUG) {
                    Toast.makeText(WishCommentActivity.this, "success", Toast.LENGTH_SHORT).show();
                }
                mAdapter = new WishCommentAdapter(WishCommentActivity.this, list);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(WishCommentActivity.this, "Failure,please retry!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListener() {
        mIvSend.setOnClickListener(v -> {
            ((InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
            String comment = mEditText.getText().toString();
            if ("".equals(comment)) {
                Toast.makeText(this, "The comment can not be empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            final WishComment wishComment = new WishComment(mOthersWish, comment);
            wishComment.save(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(WishCommentActivity.this, "发表评论成功!", Toast.LENGTH_SHORT).show();
                    mEditText.setText("");
                    mAdapter.getData().add(wishComment);
                    mAdapter.notifyItemInserted(mAdapter.getData().size());
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(WishCommentActivity.this, "Failure,please retry!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void initView() {
        ((Toolbar) findViewById(R.id.toolbar_wish_comment)).setNavigationOnClickListener(v -> onBackPressed());

        mContainer = (FrameLayout) findViewById(R.id.wish_comment_container);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_comments);
        mIvLike = (ImageView) findViewById(R.id.iv_like);
        mIvComment = (ImageView) findViewById(R.id.iv_comments);
        mTvLikeNum = (TextView) findViewById(R.id.tv_like_num);
        mEditText = (AppCompatEditText) findViewById(R.id.et_comment);
        mIvSend = (ImageView) findViewById(R.id.iv_send);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getEdgeSize() {
        return Utils.dp2px(200);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_wish_comment;
    }
}
