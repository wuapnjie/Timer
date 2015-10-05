package com.iec.dwx.timer.Views;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.iec.dwx.timer.R;

/**
 * 翻转两个子视图的布局
 * Created by Flying SnowBean on 2015/10/4.
 */
public class CardFlipLayout extends FrameLayout {
    private final String TAG = CardFlipLayout.class.getSimpleName();
    private View mBackgroundView;
    private View mForegroundView;
    private boolean isOrderChanged = false;

    private AnimatorSet mRightSet;
    private AnimatorSet mLeftSet;

    public CardFlipLayout(Context context) {
        super(context);
    }

    public CardFlipLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CardFlipLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackgroundView = getChildAt(0);
        mForegroundView = getChildAt(1);

        mBackgroundView.setRotationY(-180);
        mBackgroundView.setAlpha(0);

        initAnim();
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        Animator card_flip_left_out = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_left_out);
        card_flip_left_out.setTarget(mForegroundView);

        Animator card_flip_left_in = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_left_in);
        card_flip_left_in.setTarget(mBackgroundView);

        mLeftSet = new AnimatorSet();
        mLeftSet.playTogether(card_flip_left_out, card_flip_left_in);

        Animator card_flip_right_out = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_right_out);
        card_flip_right_out.setTarget(mBackgroundView);

        Animator card_flip_right_in = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_right_in);
        card_flip_right_in.setTarget(mForegroundView);

        mRightSet = new AnimatorSet();
        mRightSet.playTogether(card_flip_right_out, card_flip_right_in);
    }

    /**
     * 外部翻转方法
     */
    public void flip() {
        if (!isOrderChanged) {
            mLeftSet.start();
            isOrderChanged = true;
        } else {
            mRightSet.start();
            isOrderChanged = false;
        }
    }
}
