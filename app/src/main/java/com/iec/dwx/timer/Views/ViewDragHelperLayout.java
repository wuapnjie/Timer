package com.iec.dwx.timer.Views;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Runnable.SkillUpdatePositionLeft;
import com.iec.dwx.timer.Runnable.SkillUpdatePositionTop;

/**
 * Created by Administrator on 2015/10/20 0020.
 */
public class ViewDragHelperLayout extends FrameLayout {
    private final String TAG = ViewDragHelperLayout.class.getSimpleName();
    private ViewDragHelper mDragger;

    public ViewDragHelperLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback()
        {
            @Override
            public boolean tryCaptureView(View child, int pointerId)
            {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx)
            {
                System.out.println(child.getTag());
                SkillUpdatePositionLeft runnable=new SkillUpdatePositionLeft(getContext(),(int)child.getTag(R.id.tag_zero),left);
                Thread td=new Thread(runnable);
                td.start();
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy)
            {
                SkillUpdatePositionTop runnable=new SkillUpdatePositionTop(getContext(),(int)child.getTag(R.id.tag_zero),top);
                Thread td=new Thread(runnable);
                td.start();
                return top;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mDragger.processTouchEvent(event);
        return true;
    }

}
